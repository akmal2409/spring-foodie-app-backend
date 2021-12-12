package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.OptionSetDto;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.OptionSetMapper;
import com.akmal.springfoodieappbackend.model.OptionSet;
import com.akmal.springfoodieappbackend.model.Restaurant;
import com.akmal.springfoodieappbackend.repository.OptionSetRepository;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Supplier;

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
  private final MenuItemService menuItemService;
  private final RestaurantService restaurantService;
  private final OptionSetMapper optionSetMapper;
  private final OptionSetRepository optionSetRepository;
  private final TransactionRunner transactionRunner;

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
        this.menuItemService
            .findMenuItemById(setDto.menuItemId())
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
}
