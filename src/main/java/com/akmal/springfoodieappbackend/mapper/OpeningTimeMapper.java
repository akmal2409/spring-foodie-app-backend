package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.OpeningTimeDto;
import com.akmal.springfoodieappbackend.model.OpeningTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

/**
 * Interface represents a contract based on which Mapstruct library generates the mapper class at
 * runtime. Converts OpeningTime Object to DTO and vice versa.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:48 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OpeningTimeMapper {

  @Mapping(target = "restaurantId", source = "restaurant.id")
  OpeningTimeDto toDto(OpeningTime openingTime);

  @Mapping(target = "restaurant", ignore = true)
  OpeningTime from(OpeningTimeDto openingTimeDto);
}
