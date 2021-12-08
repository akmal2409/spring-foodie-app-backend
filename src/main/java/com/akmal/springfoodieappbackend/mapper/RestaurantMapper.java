package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.*;
import com.akmal.springfoodieappbackend.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Class represents a contract based on which Mapstruct library generates the mapper class at
 * runtime. Converts Restaurant Entity to DTO and vice versa
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:01 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class RestaurantMapper {
  @Autowired private AddressMapper addressMapper;

  @Autowired private CategoryMapper categoryMapper;
  @Autowired private OpeningTimeMapper openingTimeMapper;

  @Mapping(target = "address", expression = "java(mapAddressToDto(restaurant.getAddress()))")
  @Mapping(
      target = "openingTimes",
      expression = "java(mapOpeningTimesToDto(restaurant.getOpeningTimes()))")
  @Mapping(
      target = "categories",
      expression = "java(mapCategoriesToDto(restaurant.getCategories()))")
  @Mapping(
      target = "thumbnailImage",
      expression = "java(mapImageToDto(restaurant.getThumbnailImage()))")
  @Mapping(target = "fullImage", expression = "java(mapImageToDto(restaurant.getFullImage()))")
  public abstract RestaurantDto toDto(Restaurant restaurant);

  @Mapping(target = "address", expression = "java(mapToAddress(restaurantDto.address()))")
  @Mapping(
      target = "openingTimes",
      expression = "java(mapToOpeningTimes(restaurantDto.openingTimes()))")
  @Mapping(target = "categories", expression = "java(mapToCategories(restaurantDto.categories()))")
  @Mapping(
      target = "thumbnailImage",
      expression = "java(mapToImage(restaurantDto.thumbnailImage()))")
  @Mapping(target = "fullImage", expression = "java(mapToImage(restaurantDto.fullImage()))")
  public abstract Restaurant from(RestaurantDto restaurantDto);

  protected List<OpeningTimeDto> mapOpeningTimesToDto(List<OpeningTime> openingTimes) {
    return Optional.ofNullable(openingTimes).orElse(List.of()).stream()
        .map(this.openingTimeMapper::toDto)
        .toList();
  }

  protected List<OpeningTime> mapToOpeningTimes(List<OpeningTimeDto> openingTimes) {
    return Optional.ofNullable(openingTimes).orElse(List.of()).stream()
        .map(this.openingTimeMapper::from)
        .toList();
  }

  protected List<CategoryDto> mapCategoriesToDto(List<Category> categories) {
    return Optional.ofNullable(categories).orElse(List.of()).stream()
        .map(this.categoryMapper::toDto)
        .toList();
  }

  protected List<Category> mapToCategories(List<CategoryDto> categories) {
    return Optional.ofNullable(categories).orElse(List.of()).stream()
        .map(this.categoryMapper::from)
        .toList();
  }

  protected AddressDto mapAddressToDto(Address address) {
    return this.addressMapper.toDto(address);
  }

  protected Address mapToAddress(AddressDto address) {
    return this.addressMapper.from(address);
  }

  protected ImageDto mapImageToDto(Image image) {
    if (image == null) {
      return null;
    }
    return ImageDto.fromImage(image);
  }

  protected Image mapToImage(ImageDto imageDto) {
    if (imageDto == null) {
      return null;
    }
    return imageDto.toImage();
  }
}
