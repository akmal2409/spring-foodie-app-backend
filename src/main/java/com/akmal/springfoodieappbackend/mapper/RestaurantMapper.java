package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.AddressDto;
import com.akmal.springfoodieappbackend.dto.ImageDto;
import com.akmal.springfoodieappbackend.dto.LocationDto;
import com.akmal.springfoodieappbackend.dto.RestaurantDto;
import com.akmal.springfoodieappbackend.model.Address;
import com.akmal.springfoodieappbackend.model.Category;
import com.akmal.springfoodieappbackend.model.Image;
import com.akmal.springfoodieappbackend.model.OpeningTime;
import com.akmal.springfoodieappbackend.model.Restaurant;
import com.akmal.springfoodieappbackend.model.elasticsearch.ESAddress;
import com.akmal.springfoodieappbackend.model.elasticsearch.ESImage;
import com.akmal.springfoodieappbackend.model.elasticsearch.ESOpeningTime;
import com.akmal.springfoodieappbackend.model.elasticsearch.ESRestaurant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

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
      target = "openingTime",
      expression = "java(mapOpeningTimesToDto(restaurant.getOpeningTimes()))")
  @Mapping(
      target = "categories",
      expression = "java(mapCategoriesToDto(restaurant.getCategories()))")
  @Mapping(
      target = "thumbnailImage",
      expression = "java(mapImageToDto(restaurant.getThumbnailImage()))")
  @Mapping(target = "fullImage", expression = "java(mapImageToDto(restaurant.getFullImage()))")
  @Mapping(target = "open", expression = "java(isRestaurantOpen(restaurant.getOpeningTimes()))")
  public abstract RestaurantDto toDto(Restaurant restaurant);

  @Mapping(target = "address", expression = "java(mapESAddressToDto(restaurant.getAddress()))")
  @Mapping(
      target = "openingTime",
      expression = "java(mapESOpeningTimesToDto(restaurant.getOpeningTimes()))")
  @Mapping(
      target = "thumbnailImage",
      expression = "java(mapESImageToDto(restaurant.getThumbnailImage()))")
  @Mapping(target = "fullImage", expression = "java(mapESImageToDto(restaurant.getFullImage()))")
  @Mapping(target = "open", expression = "java(isESRestaurantOpen(restaurant.getOpeningTimes()))")
  public abstract RestaurantDto esModelToDto(ESRestaurant restaurant);

  @Mapping(target = "address", expression = "java(mapToAddress(restaurantDto.address()))")
  @Mapping(target = "openingTimes", ignore = true)
  @Mapping(target = "categories", ignore = true)
  @Mapping(
      target = "thumbnailImage",
      expression = "java(mapToImage(restaurantDto.thumbnailImage()))")
  @Mapping(target = "fullImage", expression = "java(mapToImage(restaurantDto.fullImage()))")
  public abstract Restaurant from(RestaurantDto restaurantDto);

  @Mapping(target = "address", expression = "java(mapToESAddress(restaurant.getAddress()))")
  @Mapping(
      target = "openingTimes",
      expression = "java(mapToESOpeningTime(restaurant.getOpeningTimes()))")
  @Mapping(
      target = "categories",
      expression = "java(mapCategoriesToDto(restaurant.getCategories()))")
  @Mapping(
      target = "thumbnailImage",
      expression = "java(mapToESImage(restaurant.getThumbnailImage()))")
  @Mapping(target = "fullImage", expression = "java(mapToESImage(restaurant.getFullImage()))")
  public abstract ESRestaurant toESModel(Restaurant restaurant);

  protected String mapOpeningTimesToDto(List<OpeningTime> openingTimes) {
    int currentDay = LocalDateTime.now().getDayOfWeek().getValue();

    return Optional.ofNullable(openingTimes).orElse(List.of()).stream()
        .filter(time -> time.getDay() == currentDay)
        .findFirst()
        .map(OpeningTime::toRangeString)
        .orElse("Closed");
  }

  protected String mapESOpeningTimesToDto(List<ESOpeningTime> openingTimes) {
    int currentDay = LocalDateTime.now().getDayOfWeek().getValue();

    return Optional.ofNullable(openingTimes).orElse(List.of()).stream()
        .filter(time -> time.getDay() == currentDay)
        .findFirst()
        .map(ESOpeningTime::toRangeString)
        .orElse("Closed");
  }

  protected boolean isRestaurantOpen(List<OpeningTime> openingTimes) {
    final LocalTime currentTime = LocalTime.now();
    int currentDay = LocalDateTime.now().getDayOfWeek().getValue();
    return Optional.ofNullable(openingTimes).orElse(List.of()).stream()
        .filter(time -> time.getDay() == currentDay)
        .findFirst()
        .map(
            time ->
                currentTime.isAfter(time.getOpenFrom()) && currentTime.isBefore(time.getOpenTill()))
        .orElse(false);
  }

  protected boolean isESRestaurantOpen(List<ESOpeningTime> openingTimes) {
    final LocalTime currentTime = LocalTime.now();
    int currentDay = LocalDateTime.now().getDayOfWeek().getValue();
    return Optional.ofNullable(openingTimes).orElse(List.of()).stream()
        .filter(time -> time.getDay() == currentDay)
        .findFirst()
        .map(
            time ->
                currentTime.isAfter(time.getOpenFrom()) && currentTime.isBefore(time.getOpenTill()))
        .orElse(false);
  }

  protected List<String> mapCategoriesToDto(List<Category> categories) {
    return Optional.ofNullable(categories).orElse(List.of()).stream()
        .map(Category::getName)
        .toList();
  }

  protected ImageDto mapESImageToDto(ESImage image) {
    return Optional.ofNullable(image)
        .map(img -> new ImageDto(img.getId(), img.getUrl(), img.getTitle()))
        .orElse(null);
  }

  protected AddressDto mapAddressToDto(Address address) {
    return this.addressMapper.toDto(address);
  }

  protected ESAddress mapToESAddress(Address address) {
    return Optional.ofNullable(address)
        .map(
            unmapped ->
                new ESAddress(
                    unmapped.getCountry(),
                    unmapped.getCity(),
                    unmapped.getPostCode(),
                    unmapped.getStreet(),
                    unmapped.getAddition(),
                    unmapped.getApartmentNumber(),
                    new GeoPoint(unmapped.getLocation().getLat(), unmapped.getLocation().getLon())))
        .orElse(null);
  }

  protected AddressDto mapESAddressToDto(ESAddress address) {
    return Optional.ofNullable(address)
        .map(
            unmapped ->
                new AddressDto(
                    unmapped.getCountry(),
                    unmapped.getCity(),
                    unmapped.getPostCode(),
                    unmapped.getStreet(),
                    unmapped.getAddition(),
                    unmapped.getApartmentNumber(),
                    new LocationDto(
                        unmapped.getLocation().getLat(), unmapped.getLocation().getLon())))
        .orElse(null);
  }

  protected List<ESOpeningTime> mapToESOpeningTime(List<OpeningTime> openingTimes) {
    return Optional.ofNullable(openingTimes).orElse(new ArrayList<>()).stream()
        .map(
            time ->
                new ESOpeningTime(
                    time.getDay(), time.getOpenFrom(), time.getOpenTill(), time.getId()))
        .toList();
  }

  protected ESImage mapToESImage(Image image) {
    return Optional.ofNullable(image)
        .map(unmapped -> new ESImage(unmapped.getUrl(), unmapped.getTitle(), unmapped.getId()))
        .orElse(null);
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
