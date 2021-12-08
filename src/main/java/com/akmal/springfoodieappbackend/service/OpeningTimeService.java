package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.OpeningTimeDto;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.OpeningTimeMapper;
import com.akmal.springfoodieappbackend.model.OpeningTime;
import com.akmal.springfoodieappbackend.model.Restaurant;
import com.akmal.springfoodieappbackend.repository.OpeningTimeRepository;
import com.akmal.springfoodieappbackend.repository.RestaurantRepository;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * The service layer implementation class that manages persistence of the {@link
 * com.akmal.springfoodieappbackend.model.OpeningTime} entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 08/12/2021 - 8:46 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OpeningTimeService {
  private final OpeningTimeRepository timeRepository;
  private final RestaurantRepository restaurantRepository;
  private final OpeningTimeMapper openingTimeMapper;
  private final TransactionRunner transactionRunner;

  /**
   * The method is responsible for saving the {@link OpeningTime} entity. Maps the accepted DTO by
   * using {@link #mapTimeWithRestaurant(OpeningTimeDto)}. Runs the mapping method in a transaction
   * runner, which helps spring to proxy the method call and run it within the same transaction.
   *
   * @param timeDto {@link OpeningTimeDto} object.
   * @return saved {@link OpeningTimeDto} object
   */
  @Transactional
  public OpeningTimeDto save(OpeningTimeDto timeDto) {
    final var mappedTime =
        this.transactionRunner.runInTransaction(() -> this.mapTimeWithRestaurant(timeDto));

    return this.openingTimeMapper.toDto(this.timeRepository.save(mappedTime));
  }

  /**
   * The method is responsible for updating the existing {@link OpeningTime} entity by ID.
   *
   * @throws NotFoundException if the {@link OpeningTime} entity was not found.
   * @param timeDto {@link OpeningTimeDto} object.
   * @param id of an existing {@link OpeningTime} entity.
   * @return updated {@link OpeningTimeDto} object.
   */
  @Transactional
  public OpeningTimeDto update(OpeningTimeDto timeDto, Long id) {
    final var existingTime =
        this.timeRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format("The OpeningTime entity with ID %d was not found", id)));

    final var mappedTime =
        this.transactionRunner.runInTransaction(
            () -> this.mapTimeWithRestaurant(timeDto).withId(existingTime.getId()));

    return this.openingTimeMapper.toDto(this.timeRepository.save(mappedTime));
  }

  /**
   * The method is responsible for finding the {@link Restaurant} by {@link
   * OpeningTimeDto#restaurantId()}, mapping the DTO object to {@link OpeningTime} model and
   * creating an immutable copy of it with an existing {@link Restaurant}.
   *
   * @throws NotFoundException if the {@link Restaurant} entity has not been found.
   * @param timeDto {@link OpeningTimeDto} object.
   * @return {@link OpeningTime} entity with an existing {@link Restaurant} entity.
   */
  private OpeningTime mapTimeWithRestaurant(OpeningTimeDto timeDto) {
    Objects.requireNonNull(timeDto, "Opening Time DTO object must not be null");

    final var existingRestaurant =
        this.restaurantRepository
            .findById(timeDto.restaurantId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            "Restaurant with ID %d was not found", timeDto.restaurantId())));

    return this.openingTimeMapper.from(timeDto).withRestaurant(existingRestaurant);
  }

  @Transactional
  public void deleteById(Long id) {
    this.restaurantRepository.deleteById(id);
  }
}
