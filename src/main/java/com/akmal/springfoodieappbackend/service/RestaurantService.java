package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.RestaurantDto;
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
}
