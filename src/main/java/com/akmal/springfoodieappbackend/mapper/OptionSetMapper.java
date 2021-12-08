package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.OptionDto;
import com.akmal.springfoodieappbackend.dto.OptionSetDto;
import com.akmal.springfoodieappbackend.model.Option;
import com.akmal.springfoodieappbackend.model.OptionSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Class represents a contract based on which Mapstruct library generates the mapper class at
 * runtime. Converts OptionSer Object to DTO and vice versa.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:58 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class OptionSetMapper {
  @Autowired private OptionMapper optionMapper;

  @Mapping(target = "optionSetType", source = "optionSetType.type")
  @Mapping(target = "options", expression = "java(mapOptionsToDto(optionSet.getOptions()))")
  @Mapping(target = "menuItemId", source = "menuItem.id")
  public abstract OptionSetDto toDto(OptionSet optionSet);

  @Mapping(
      target = "optionSetType",
      expression = "java(mapOptionSetType(optionSetDto.optionSetType()))")
  @Mapping(target = "options", expression = "java(mapToOptions(optionSetDto.options()))")
  @Mapping(target = "menuItem", ignore = true)
  public abstract OptionSet from(OptionSetDto optionSetDto);

  protected OptionSet.OptionSetType mapOptionSetType(String type) {
    return OptionSet.OptionSetType.from(type);
  }

  protected List<OptionDto> mapOptionsToDto(List<Option> options) {
    return Optional.ofNullable(options).orElse(List.of()).stream()
        .map(this.optionMapper::toDto)
        .toList();
  }

  protected List<Option> mapToOptions(List<OptionDto> options) {
    return Optional.ofNullable(options).orElse(List.of()).stream()
        .map(this.optionMapper::from)
        .toList();
  }
}
