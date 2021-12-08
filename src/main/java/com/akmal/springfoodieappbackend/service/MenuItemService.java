package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.MenuItemDto;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.MenuItemMapper;
import com.akmal.springfoodieappbackend.model.Menu;
import com.akmal.springfoodieappbackend.model.MenuItem;
import com.akmal.springfoodieappbackend.repository.MenuItemRepository;
import com.akmal.springfoodieappbackend.repository.MenuRepository;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * The class represents the service of the {@link com.akmal.springfoodieappbackend.model.MenuItem}
 * entity. Manages the persistence of the entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/11/2021 - 8:50 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MenuItemService {

  private final MenuItemRepository menuItemRepository;
  private final MenuItemMapper menuItemMapper;
  private final MenuRepository menuRepository;
  private final RestaurantService restaurantService;
  private final TransactionRunner transactionRunner;

  /**
   * The method is responsible for deleting all the image references for a given image ID. It
   * iterates through all the matching items and sets its thumbnail and full image fields to null if
   * they match the given id.
   *
   * @param imageId of an {@link com.akmal.springfoodieappbackend.model.Image} entity that is going
   *     to be removed.
   */
  @Transactional
  public void removeImageReferences(String imageId) {
    Objects.requireNonNull(imageId, "Image id cannot be null");
    final Iterable<MenuItem> menuItems =
        this.menuItemRepository.findAllByThumbnailImageOrFullImageId(imageId);

    for (MenuItem menuItem : menuItems) {
      MenuItem updatedMenuItem = menuItem;
      if (menuItem.getThumbnailImage() != null
          && imageId.equals(menuItem.getThumbnailImage().getId())) {
        updatedMenuItem = updatedMenuItem.withThumbnailImage(null);
      }

      if (menuItem.getFullImage() != null && imageId.equals(menuItem.getFullImage().getId())) {
        updatedMenuItem = updatedMenuItem.withFullImage(null);
      }
      this.menuItemRepository.save(updatedMenuItem);
    }
  }

  /**
   * The method finds the entity by ID. if an entity is not found, it returns null.
   *
   * @param id of an {@link MenuItem} entity.
   * @return {@link MenuItem} object.
   */
  @Transactional(readOnly = true)
  public MenuItemDto findById(Long id) {
    return this.menuItemRepository.findById(id).map(this.menuItemMapper::toDto).orElse(null);
  }

  /**
   * The method is responsible for saving the menu item as a new object, to ensure that it is
   * persisted as a new object, we have to set the id to null regardless of what values the client
   * has presented us.
   *
   * @param menuItemDto {@link MenuItemDto} object.
   * @return {@link MenuItemDto} saved object.
   */
  @Transactional
  public MenuItemDto save(MenuItemDto menuItemDto) {
    final var existingMenu =
        this.transactionRunner.runInTransaction(() -> this.getExistingMenu(menuItemDto.menuId()));

    final var mappedMenuItem =
        this.menuItemMapper.from(menuItemDto).withMenu(existingMenu).withId(null);

    return this.menuItemMapper.toDto(this.menuItemRepository.save(mappedMenuItem));
  }

  /**
   * The method is responsible for updating the existing {@link MenuItem} entity given the ID.
   * Firstly, it finds the existing menu and checks the ownership of the restaurant to which the
   * menu belongs to. Thereafter, maps the {@link MenuItemDto} to the POJO and persists it.
   *
   * @param menuItemDto {@link MenuItemDto} object.
   * @param id of an existing {@link MenuItem} entity.
   * @return {@link MenuItemDto} object.
   * @throws NotFoundException if the {@link MenuItem} is not found.
   */
  @Transactional
  public MenuItemDto update(MenuItemDto menuItemDto, Long id) {
    final var existingMenu =
        this.transactionRunner.runInTransaction(() -> this.getExistingMenu(menuItemDto.menuId()));

    final var existingMenuItem =
        this.menuItemRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(String.format("MenuItem with ID %d was not found", id)));

    final var mappedMenuItem =
        this.menuItemMapper
            .from(menuItemDto)
            .withId(existingMenuItem.getId())
            .withMenu(existingMenu);

    return this.menuItemMapper.toDto(this.menuItemRepository.save(mappedMenuItem));
  }

  /**
   * The method is responsible to delete {@link MenuItem} by ID.
   *
   * @param id of a {@link MenuItem} entity.
   */
  @Transactional
  public void deleteById(Long id) {
    this.menuItemRepository.deleteById(id);
  }

  /**
   * The method is responsible for fetching and converting the {@link MenuItem} entity by {@code
   * menuId} referencing {@link Menu}.
   *
   * @param menuId of the {@link Menu} entity.
   * @return {@link MenuItemDto} objects.
   */
  @Transactional(readOnly = true)
  public List<MenuItemDto> findByMenuId(Long menuId) {
    return this.menuItemRepository.findAllByMenuId(menuId).stream()
        .map(this.menuItemMapper::toDto)
        .toList();
  }

  /**
   * The method is responsible for retrieving the existing menu item from the database given the
   * {@code menuId} argument and verifies the ownership of the current user and the {@link
   * com.akmal.springfoodieappbackend.model.Restaurant} entity the menu belongs to.
   *
   * @param menuId id of a {@link Menu} entity
   * @return {@link Menu} instance object.
   * @throws com.akmal.springfoodieappbackend.exception.InsufficientRightsException if the current
   *     user is not restaurant owner to which menu belongs to.
   */
  private Menu getExistingMenu(Long menuId) {
    final var existingMenu =
        this.menuRepository
            .findById(menuId)
            .orElseThrow(
                () ->
                    new NotFoundException(String.format("Menu with ID %s was not found", menuId)));
    this.restaurantService.verifyUserIsOwner(existingMenu.getRestaurant());
    return existingMenu;
  }
}
