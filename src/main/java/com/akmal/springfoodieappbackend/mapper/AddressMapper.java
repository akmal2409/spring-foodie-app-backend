package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.AddressDto;
import com.akmal.springfoodieappbackend.dto.LocationDto;
import com.akmal.springfoodieappbackend.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

/**
 * Class represents a contract based on which Mapstruct library generates the mapper class at
 * runtime. Converts Address Object to DTO and vice versa. Due to close relationship between an
 * Address object and a Location object, because both of them exist only together, the mapping class
 * includes the methods to map the location
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:04 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class AddressMapper {

  @Mapping(target = "location", expression = "java(mapLocationToDto(address.getLocation()))")
  public abstract AddressDto toDto(Address address);

  @Mapping(target = "location", expression = "java(mapToLocation(addressDto.location()))")
  public abstract Address from(AddressDto addressDto);

  protected LocationDto mapLocationToDto(Address.Location location) {
    return new LocationDto(location.getLat(), location.getLon());
  }

  protected Address.Location mapToLocation(LocationDto location) {
    return new Address.Location(location.lat(), location.lon());
  }
}
