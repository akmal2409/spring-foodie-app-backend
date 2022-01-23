package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.MenuItemConfigurationDto;
import com.akmal.springfoodieappbackend.dto.OptionSetDto;
import com.akmal.springfoodieappbackend.exception.InvalidConfigurationException;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.OptionSetMapper;
import com.akmal.springfoodieappbackend.model.Option;
import com.akmal.springfoodieappbackend.model.OptionSet;
import com.akmal.springfoodieappbackend.model.Restaurant;
import com.akmal.springfoodieappbackend.repository.MenuItemRepository;
import com.akmal.springfoodieappbackend.repository.OptionRepository;
import com.akmal.springfoodieappbackend.repository.OptionSetRepository;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class represents the service layer that manages the persistence of {@link OptionSet} entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/11/2021 - 8:50 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OptionSetService {
  private final RestaurantService restaurantService;
  private final OptionSetMapper optionSetMapper;
  private final OptionSetRepository optionSetRepository;
  private final TransactionRunner transactionRunner;
  private final OptionRepository optionRepository;
  private final MenuItemRepository menuItemRepository;

  /**
   * The method is responsible for updating the {@link OptionSet} entity. It requires the entity to
   * be present on the time of update.
   *
   * @throws NotFoundException if the {@link OptionSet} is not found.
   * @param setDto {@link OptionSetDto} object.
   * @param setId of the existing {@link OptionSet} entity.
   * @return {@link OptionSetDto} mapped and saved object.
   */
  @Transactional
  public OptionSetDto update(OptionSetDto setDto, Long setId) {
    final var existingSet =
        this.optionSetRepository
            .findById(setId)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format("OptionSet with an id %d was not found", setId)));

    final var setWithId = setDto.withId(existingSet.getId());

    return this.transactionRunner.runInTransaction(() -> this.save(setWithId));
  }

  /**
   * The method is responsible for mapping the dto object and calling {@link
   * OptionSetService#saveAndConvert(OptionSet)} that saves the entity and converts it back to DTO.
   * Note: It utilizes the {@link TransactionRunner} in particular {@link
   * TransactionRunner#runInTransaction(Supplier)} in order to ensure that the methods are executed
   * in the same transaction, if one of the fails, entire operation will be rolled back.
   *
   * @param setDto valid {@link OptionSetDto} object.
   * @return {@link OptionSetDto} saved object.
   */
  @Transactional
  public OptionSetDto save(OptionSetDto setDto) {
    final var mappedSet = this.transactionRunner.runInTransaction(() -> convertAndValidate(setDto));

    return this.transactionRunner.runInTransaction(() -> this.saveAndConvert(mappedSet));
  }

  /**
   * The method is responsible for validating provided selected options. It constructs two hashmap,
   * namely setMap and optionMap. The setMap contains fetched {@link OptionSet} entities and acts as
   * a cache, since multiple selected options might relate to the same option set. The second
   * hashmap, optionMap, is used to group the options together based on a key, which in this case is
   * the OptionSet object.
   *
   * @throws InvalidConfigurationException If the {@link OptionSet} doesn't contain the selected
   *     option
   * @param selectedOptions {@link
   *     com.akmal.springfoodieappbackend.dto.MenuItemConfigurationDto.SelectedOption} DTO
   * @return a flattened list of {@link Option}
   */
  @Transactional(readOnly = true)
  public List<Option> validateSelectedOptions(
      List<MenuItemConfigurationDto.SelectedOption> selectedOptions) {
    Objects.requireNonNull(selectedOptions, "Selected options must not be null");
    final Map<Long, OptionSet> setMap = new HashMap<>(); // used for caching, not to look up again
    final Map<OptionSet, List<Option>> optionMap = new HashMap<>();

    for (MenuItemConfigurationDto.SelectedOption selectedOption : selectedOptions) {
      Objects.requireNonNull(selectedOption, "Selected option was null");

      Option option =
          this.transactionRunner.runInTransaction(
              () -> this.findOptionById(selectedOption.optionId()));
      OptionSet set =
          setMap.getOrDefault(
              selectedOption.optionSetId(), this.findOptionSetById(selectedOption.optionSetId()));

      if (!setMap.containsKey(set.getId())) { // cache for subsequent queries.
        setMap.put(set.getId(), set);
      }

      boolean validOption =
          set.getOptions().stream().anyMatch(o -> Objects.equals(o.getId(), option.getId()));
      if (validOption) {
        optionMap.computeIfAbsent(set, k -> new LinkedList<>());
        optionMap.get(set).add(option);
      } else {
        throw new InvalidConfigurationException(
            String.format(
                "Option with ID %d does not belong to the set with ID %d",
                option.getId(), set.getId()));
      }
    }

    for (Map.Entry<OptionSet, List<Option>> entry : optionMap.entrySet()) {
      // run in the same transaction, in order to be able to rollback
      this.transactionRunner.runInTransaction(
          () -> this.validateOptionSetConstraints(entry.getKey(), entry.getValue()));
    }

    return optionMap.values().stream().flatMap(Collection::stream).toList();
  }

  /**
   * The method is responsible for checking two invariants associated with the {@link OptionSet}.
   * Firstly, if the option set is exclusive, meaning it can have only one selected option (e.g. one
   * type of glazing) Secondly, if the maximum option is not specified as -1, which is the default
   * value, and selected options exceed that number, then the exception will be thrown.
   *
   * @throws InvalidConfigurationException if the "exclusiveness" is violated or the maximu number
   *     of items was exceeded.
   * @param set {@link OptionSet}
   * @param options {@link List<Option>}
   */
  private void validateOptionSetConstraints(OptionSet set, List<Option> options) {
    if (set.isExclusive() && options.size() > 1) {
      throw new InvalidConfigurationException(
          String.format("Option set with ID %d can have only 1 selected option", set.getId()));
    }

    if (set.getMaximumOptionsSelected() != -1 && set.getMaximumOptionsSelected() < options.size()) {
      throw new InvalidConfigurationException(
          String.format(
              "Option set with ID %d cannot have more than %d selected items. Provided: %d",
              set.getId(), set.getMaximumOptionsSelected(), options.size()));
    }
  }

  private Option findOptionById(long id) {
    return this.optionRepository
        .findById(id)
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format("Option " + "with ID %d was " + "not found", id)));
  }

  private OptionSet findOptionSetById(long id) {
    return this.optionSetRepository
        .findById(id)
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format("Option set " + "with ID %d was not found", id)));
  }

  /**
   * The method is responsible for fetching the existing {@link
   * com.akmal.springfoodieappbackend.model.MenuItem} by id using the {@link
   * MenuItemService#findMenuItemById(Long)}. Thereafter, it checks if the {@link
   * com.akmal.springfoodieappbackend.model.MenuItem} is associated with the existing menu and
   * whether the menu itself has a linked restaurant. If conditions are met, then by the means of
   * the {@link RestaurantService#verifyUserIsOwner(Restaurant)} it will check if the current user
   * is the owner of the resource that is being updated.
   *
   * @throws NotFoundException if the {@link com.akmal.springfoodieappbackend.model.MenuItem} is not
   *     found.
   * @param setDto {@link OptionSetDto} object.
   * @return {@link OptionSet} mapped entity.
   */
  private OptionSet convertAndValidate(OptionSetDto setDto) {
    final var existingMenuItem =
        this.menuItemRepository
            .findById(setDto.menuItemId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            "Menu item with an ID %d was not found", setDto.menuItemId())));

    final var existingMenu = existingMenuItem.getMenu();

    if (existingMenu != null && existingMenu.getRestaurant() != null) {
      this.restaurantService.verifyUserIsOwner(existingMenu.getRestaurant());
    }

    return this.optionSetMapper.from(setDto).withMenuItem(existingMenuItem);
  }

  /**
   * The method is responsible for saving the {@link OptionSet} entity and converting it to the
   * {@link OptionSetDto} object.
   *
   * @param optionSet {@link OptionSet} object.
   * @return {@link OptionSetDto} object.
   */
  private OptionSetDto saveAndConvert(OptionSet optionSet) {
    final var savedSet = this.optionSetRepository.save(optionSet);

    return this.optionSetMapper.toDto(savedSet);
  }

  /**
   * The method is responsible for deleting {@link OptionSet} entity by ID. If the entity is not
   * found, the method returns. It will not verify ownership in following cases:
   *
   * <ol>
   *   <li>If existing {@link com.akmal.springfoodieappbackend.model.MenuItem} does not exist
   *   <li>If existing {@link com.akmal.springfoodieappbackend.model.Menu} does not exist
   * </ol>
   *
   * @param id of an existing {@link OptionSet} entity.
   */
  public void deleteById(long id) {
    final var existingSetOptional = this.optionSetRepository.findById(id);

    if (existingSetOptional.isEmpty()) {
      return;
    }

    final var existingMenuItem = existingSetOptional.get().getMenuItem();

    if (existingMenuItem != null) {
      final var existingMenu = existingMenuItem.getMenu();

      if (existingMenu != null && existingMenu.getRestaurant() != null) {
        this.restaurantService.verifyUserIsOwner(existingMenu.getRestaurant());
      }
    }

    this.optionSetRepository.deleteById(id);
  }
}
