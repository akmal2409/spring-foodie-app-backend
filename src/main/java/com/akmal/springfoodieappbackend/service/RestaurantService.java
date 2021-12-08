package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.exception.InsufficientRightsException;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.RestaurantMapper;
import com.akmal.springfoodieappbackend.model.OpeningTime;
import com.akmal.springfoodieappbackend.model.Restaurant;
import com.akmal.springfoodieappbackend.repository.OpeningTimeRepository;
import com.akmal.springfoodieappbackend.repository.RestaurantRepository;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * The {@code RestaurantService} is a service class that abstracts the business logic from the
 * controller. Uses underlying data repositories for data retrieval and processing of the {@link
 * com.akmal.springfoodieappbackend.model.Restaurant} class.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 11:53 AM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class RestaurantService {
  private final RestaurantRepository restaurantRepository;
  private final RestaurantMapper restaurantMapper;
  private final OpeningTimeRepository openingTimeRepository;
  private final UserService userService;
  private final TransactionRunner transactionRunner;

  /**
   * The method is responsible for finding all restaurants with specified page and size offsets. The
   * return object is converted into DTO.
   *
   * @param page - page index (zero based indexing)
   * @param size - number of elements in a page
   * @return page - List of restaurants within the offset
   */
  @Transactional(readOnly = true)
  public Page<RestaurantDto> findAll(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);

    return this.restaurantRepository.findAll(pageRequest).map(this.restaurantMapper::toDto);
  }

  /**
   * The method is responsible for returning the Restaurant entity by id. If the value is not found
   * it will return null. Otherwise it will map {@link
   * com.akmal.springfoodieappbackend.model.Restaurant} to a DTO object {@link RestaurantDto}
   *
   * @param id - of a Restaurant Entity
   * @return restaurantDTO - {@link RestaurantDto}
   */
  @Transactional(readOnly = true)
  public RestaurantDto findById(long id) {
    return this.restaurantRepository.findById(id).map(this.restaurantMapper::toDto).orElse(null);
  }

  /**
   * The method is responsible for saving the {@link
   * com.akmal.springfoodieappbackend.model.Restaurant} entity. Firstly, it converts the {@link
   * RestaurantDto} object to the entity model, adds the current user ID and persists it into the
   * Database.
   *
   * @param restaurantDto - restaurant dto object
   * @return saved {@link com.akmal.springfoodieappbackend.model.Restaurant} entity mapped to DTO
   *     {@link RestaurantDto}
   */
  @Transactional
  public RestaurantDto save(RestaurantDto restaurantDto) {
    final var currentUser = this.userService.getCurrentUser();

    final var restaurant =
        this.restaurantMapper.from(restaurantDto).withOwnerId(currentUser.userId());

    this.linkOpeningTimes(restaurant);

    final var savedRestaurant = this.restaurantRepository.save(restaurant);

    return this.restaurantMapper.toDto(savedRestaurant);
  }

  /**
   * The method is responsible for updating the {@link
   * com.akmal.springfoodieappbackend.model.Restaurant} entity. It expects the ID of an existing
   * restaurant and a valid {@link RestaurantDto} object.
   *
   * @param id - ID of existing restaurant in the database
   * @param restaurantDto - valid restaurant DTO object
   * @return updated {@link com.akmal.springfoodieappbackend.model.Restaurant} entity mapped to DT
   *     O{@link RestaurantDto}
   * @throws NotFoundException in case the restaurant with the provided ID is not found
   * @throws InsufficientRightsException if the user is not the owner of the resource
   */
  @Transactional
  public RestaurantDto update(long id, RestaurantDto restaurantDto) {
    final var existingRestaurant =
        this.restaurantRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format("Restaurant entity with ID %S was not found", id)));
    final var currentUser = this.userService.getCurrentUser();

    this.transactionRunner.runInTransaction(() -> this.verifyUserIsOwner(existingRestaurant));

    this.linkOpeningTimes(existingRestaurant);

    final var updatedRestaurant =
        this.restaurantMapper
            .from(restaurantDto)
            .withId(existingRestaurant.getId())
            .withOwnerId(currentUser.userId());

    return this.restaurantMapper.toDto(this.restaurantRepository.save(updatedRestaurant));
  }

  /**
   * The method is responsible for deleting the {@link
   * com.akmal.springfoodieappbackend.model.Restaurant} entity by ID.
   *
   * @param id - of existing restaurant
   * @throws NotFoundException if the restaurant was not found
   * @throws InsufficientRightsException if the user is not the owner of the resource
   */
  @Transactional
  public void deleteById(long id) {
    final var existingRestaurant =
        this.restaurantRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format("Restaurant entity with ID %S was not found", id)));

    this.transactionRunner.runInTransaction(() -> this.verifyUserIsOwner(existingRestaurant));

    this.restaurantRepository.deleteById(id);
  }

  /**
   * The method is responsible for deleting all the restaurant entities in the database. It is
   * accessible solely to the administrator.
   */
  @Transactional
  public void deleteAll() {
    this.restaurantRepository.deleteAll();
  }

  /**
   * The method is responsible for finding all the {@link
   * com.akmal.springfoodieappbackend.model.Restaurant} entities that reference particular image id
   * and sets those fields to null. The workflow is following, if the image id equals to thumbnail
   * image ID, then it sets the field to null, same for full image.
   *
   * @param imageId - of the to be removed image
   * @throws NullPointerException if the {@code imageId} is null.
   */
  @Transactional
  public void removeImageReferences(String imageId) {
    Objects.requireNonNull(imageId, "Image id cannot be null");
    final Iterable<Restaurant> restaurants =
        this.restaurantRepository.findAllByThumbnailImageOrFullImageId(imageId);

    for (Restaurant restaurant : restaurants) {
      Restaurant updatedRestaurant = restaurant;
      if (restaurant.getThumbnailImage() != null
          && imageId.equals(restaurant.getThumbnailImage().getId())) {
        updatedRestaurant = updatedRestaurant.withThumbnailImage(null);
      }

      if (restaurant.getFullImage() != null && imageId.equals(restaurant.getFullImage().getId())) {
        updatedRestaurant = updatedRestaurant.withFullImage(null);
      }
      this.restaurantRepository.save(updatedRestaurant);
    }
  }

  /**
   * Helper method that checks if the current user is the owner of the resource.
   *
   * @param restaurant {@link Restaurant} entity.
   * @throws InsufficientRightsException if user id does not match restaurant owner id.
   */
  @Transactional
  public void verifyUserIsOwner(Restaurant restaurant) {
    Objects.requireNonNull(restaurant, "Restaurant must not be null");
    final var currentUser = this.userService.getCurrentUser();

    if (!StringUtils.hasText(currentUser.userId())
        || !StringUtils.hasText(restaurant.getOwnerId())
        || !currentUser.userId().equals(restaurant.getOwnerId())) {
      throw new InsufficientRightsException(
          "You must be the owner of the resource in order to modify it");
    }
  }

  /**
   * The method is responsible for setting the relation to the parent from {@link OpeningTime} to
   * {@link Restaurant} due to JPA nature the owning side of a ManyToMany relationship is required
   * to manage the persistence of the parent in the relationship.
   *
   * @param restaurant {@link Restaurant} entity.
   */
  private void linkOpeningTimes(final Restaurant restaurant) {
    if (restaurant.getOpeningTimes() != null) {
      for (OpeningTime time : restaurant.getOpeningTimes()) {
        time.setRestaurant(restaurant);
      }
    }
  }
}
