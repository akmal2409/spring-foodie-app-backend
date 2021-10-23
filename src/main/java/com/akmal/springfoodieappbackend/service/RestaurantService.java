package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code RestaurantService} is a service class that abstracts the business logic from the controller.
 * Uses underlying data repositories for data retrieval and processing.
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

  /**
   * The method is responsible for finding all restaurants with specified
   * page and size offsets.
   * The return object is converted into DTO.
   * @param page - page index (zero based indexing)
   * @param size - number of elements in a page
   * @return page - List of restaurants within the offset
   */
  @Transactional(readOnly = true)
  public Page<RestaurantDto> findAll(int page, int size) {

  }
}
