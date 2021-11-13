package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.exception.InsufficientRightsException;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.RestaurantMapper;
import com.akmal.springfoodieappbackend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code RestaurantService} is a service class that abstracts the business logic from the controller.
 * Uses underlying data repositories for data retrieval and processing of the
 * {@link com.akmal.springfoodieappbackend.model.Restaurant} class.
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
  private final UserService userService;

  /**
   * The method is responsible for finding all restaurants with specified
   * page and size offsets.
   * The return object is converted into DTO.
   *
   * @param page - page index (zero based indexing)
   * @param size - number of elements in a page
   * @return page - List of restaurants within the offset
   */
  @Transactional(readOnly = true)
  public Page<RestaurantDto> findAll(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);

    return this.restaurantRepository.findAll(pageRequest)
            .map(this.restaurantMapper::toDto);
  }

  /**
   * The method is responsible for returning the Restaurant entity by id.
   * If the value is not found it will return null. Otherwise it will map
   * {@link com.akmal.springfoodieappbackend.model.Restaurant} to a DTO object
   * {@link RestaurantDto}
   *
   * @param id - of a Restaurant Entity
   * @return restaurantDTO - {@link RestaurantDto}
   */
  @Transactional(readOnly = true)
  public RestaurantDto findById(long id) {
    return this.restaurantRepository
            .findById(id)
            .map(this.restaurantMapper::toDto)
            .orElse(null);
  }

  /**
   * The method is responsible for saving the {@link com.akmal.springfoodieappbackend.model.Restaurant} entity.
   * Firstly, it converts the {@link RestaurantDto} object to the entity model,
   * adds the current user ID and persists it into the Database.
   *
   * @param restaurantDto - restaurant dto object
   * @return saved {@link com.akmal.springfoodieappbackend.model.Restaurant} entity mapped to DTO {@link RestaurantDto}
   */
  @Transactional
  public RestaurantDto save(RestaurantDto restaurantDto) {
    final var currentUser = this.userService.getCurrentUser();

    final var restaurant = this.restaurantMapper.from(restaurantDto)
            .withOwnerId(currentUser.getUserId());

    final var savedRestaurant = this.restaurantRepository.save(restaurant);
    return this.restaurantMapper.toDto(savedRestaurant);
  }

  /**
   * The method is responsible for updating the {@link com.akmal.springfoodieappbackend.model.Restaurant} entity.
   * It expects the ID of an existing restaurant and a valid {@link RestaurantDto} object.
   *
   * @param id            - ID of existing restaurant in the database
   * @param restaurantDto - valid restaurant DTO object
   * @return updated {@link com.akmal.springfoodieappbackend.model.Restaurant} entity mapped to DT O{@link RestaurantDto}
   * @throws NotFoundException           in case the restaurant with the provided ID is not found
   * @throws InsufficientRightsException if the user is not the owner of the resource
   */
  @Transactional
  public RestaurantDto update(long id, RestaurantDto restaurantDto) {
    final var existingRestaurant = this.restaurantRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Restaurant entity with ID %S was not found", id)));
    final var currentUser = this.userService.getCurrentUser();

    if (!currentUser.getUserId().equals(existingRestaurant.getOwnerId())) {
      throw new InsufficientRightsException("You must be the owner of the resource in order to modify it");
    }

    final var updatedRestaurant = this.restaurantMapper.from(restaurantDto)
            .withId(existingRestaurant.getId())
            .withOwnerId(currentUser.getUserId());

    return this.restaurantMapper.toDto(this.restaurantRepository.save(updatedRestaurant));
  }

  /**
   * The method is responsible for deleting the {@link com.akmal.springfoodieappbackend.model.Restaurant} entity
   * by ID.
   *
   * @param id - of existing restaurant
   * @throws NotFoundException           if the restaurant was not found
   * @throws InsufficientRightsException if the user is not the owner of the resource
   */
  @Transactional
  public void deleteById(long id) {
    final var existingRestaurant = this.restaurantRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Restaurant entity with ID %S was not found", id)));
    final var currentUser = this.userService.getCurrentUser();

    if (!currentUser.getUserId().equals(existingRestaurant.getOwnerId())) {
      throw new InsufficientRightsException("You must be the owner of the resource in order to modify it");
    }

    this.restaurantRepository.deleteById(id);
  }
}
