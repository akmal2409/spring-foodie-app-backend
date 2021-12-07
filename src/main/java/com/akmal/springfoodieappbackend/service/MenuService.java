package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.MenuDto;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.MenuMapper;
import com.akmal.springfoodieappbackend.model.Menu;
import com.akmal.springfoodieappbackend.model.Restaurant;
import com.akmal.springfoodieappbackend.repository.MenuRepository;
import com.akmal.springfoodieappbackend.repository.RestaurantRepository;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Service class that manages the data transformation of the {@link com.akmal.springfoodieappbackend.model.Menu}
 * entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 27/11/2021 - 2:15 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MenuService {
  private final MenuRepository menuRepository;
  private final RestaurantRepository restaurantRepository;
  private final MenuMapper menuMapper;
  private final TransactionRunner transactionRunner;
  private final RestaurantService restaurantService;


  /**
   * The method is responsible for finding all {@link Menu} entity objects by {@code restaurantId} and
   * returns paginated result for a given page and size.
   *
   * @param restaurantId of a {@link com.akmal.springfoodieappbackend.model.Restaurant} entity.
   * @param page         number.
   * @param size         page size.
   * @return {@link Page} wrapping {@link com.akmal.springfoodieappbackend.dto.MenuDto} entity.
   * @throws NotFoundException if restaurant was not found by ID.
   */
  @Transactional(readOnly = true)
  public Page<MenuDto> findAllByRestaurantId(long restaurantId, int page, int size) {
    final var restaurant = this.transactionRunner
            .runInTransaction(() -> this.findRestaurantById(restaurantId));

    return this.menuRepository
            .findAllByRestaurantId(PageRequest.of(page, size), restaurant.getId())
            .map(this.menuMapper::toDto);
  }

  /**
   * The method is responsible for finding the {@link Menu} entity by ID.
   *
   * @param id of {@link Menu} entity.
   * @return {@link MenuDto} object.
   */
  @Transactional(readOnly = true)
  public MenuDto findById(long id) {
    final var menu = this.menuRepository.findById(id)
            .orElse(null);

    return this.menuMapper.toDto(menu);
  }

  /**
   * The method is responsible for saving a new {@link Menu} entity to the database.
   * Requires the restaurant to be present and the current user being the owner of the resource.
   *
   * @param menuDto {@link MenuDto} object
   * @return {@link MenuDto} saved object.
   * @throws com.akmal.springfoodieappbackend.exception.InsufficientRightsException if the user is not the owner
   *                                                                                of the restaurant.
   */
  @Transactional
  public MenuDto save(MenuDto menuDto) {
    final var restaurant = this.transactionRunner
            .runInTransaction(() -> this.findRestaurantById(menuDto.restaurantId()));

    final var mappedMenu = this.menuMapper.from(menuDto)
            .withRestaurant(restaurant);

    this.restaurantService.verifyUserIsOwner(restaurant);

    return this.menuMapper.toDto(this.menuRepository.save(mappedMenu));
  }

  /**
   * Method is responsible for updating the existing {@link Menu} entity in the
   * database.
   *
   * @param menuDto {@link MenuDto} object
   * @param id      of an existing {@link Menu} entity.
   * @return {@link MenuDto} object.
   * @throws NotFoundException                                                      if the menu was not found.
   * @throws com.akmal.springfoodieappbackend.exception.InsufficientRightsException if the user is not the owner.
   */
  @Transactional
  public MenuDto update(MenuDto menuDto, long id) {
    final var restaurant = this.transactionRunner
            .runInTransaction(() -> this.findRestaurantById(menuDto.restaurantId()));
    final var existingMenu = this.menuRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Menu with ID %d was not found", id)));

    this.restaurantService.verifyUserIsOwner(restaurant);
    final var mappedMenu = this.menuMapper.from(menuDto)
            .withRestaurant(restaurant)
            .withId(existingMenu.getId());

    return this.menuMapper.toDto(this.menuRepository.save(mappedMenu));
  }

  /**
   * The method is responsible for deleting the {@link Menu} enity from the database.
   *
   * @param id of an existing menu item.
   * @throws com.akmal.springfoodieappbackend.exception.InsufficientRightsException if the user is not owner of the
   *                                                                                resource.
   */
  @Transactional
  public void deleteById(long id) {
    final var existingMenuOptional = this.menuRepository.findById(id);

    if (existingMenuOptional.isEmpty()) {
      return;
    }

    this.restaurantService.verifyUserIsOwner(existingMenuOptional.get().getRestaurant());
    this.menuRepository.deleteById(id);
  }

  /**
   * Helper method that returns the restaurant by ID and if not found, throws an Exception.
   *
   * @param id of {@link Restaurant} entity.
   * @return {@link Restaurant} object.
   * @throws NotFoundException if restaurant not found.
   */
  private Restaurant findRestaurantById(long id) {
    return this.restaurantRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id %d was not found", id)));
  }
}
