package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.MenuItemDto;
import com.akmal.springfoodieappbackend.dto.OptionSetDto;
import com.akmal.springfoodieappbackend.model.MenuItem;
import com.akmal.springfoodieappbackend.model.OptionSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class represents a contract based on which Mapstruct library generates the
 * mapper class at runtime. Converts MenuItem Object to DTO and vice versa.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:55 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class MenuItemMapper {
  @Autowired
  private OptionSetMapper optionSetMapper;

  @Mapping(target = "menuId", source = "menuItem.menu.id")
  @Mapping(target = "optionSets", expression = "java(mapOptionSetsToDto(menuItem.getOptionSets()))")
  public abstract MenuItemDto toDto(MenuItem menuItem);

  @Mapping(target = "menu", ignore = true)
  @Mapping(target = "optionSets", expression = "java(mapToOptionSets(menuItemDto.optionSets()))")
  public abstract MenuItem from(MenuItemDto menuItemDto);

  protected List<OptionSetDto> mapOptionSetsToDto(List<OptionSet> optionSets) {
    return Optional.ofNullable(optionSets)
            .orElse(List.of())
            .stream()
            .map(this.optionSetMapper::toDto)
            .collect(Collectors.toList());
  }

  protected List<OptionSet> mapToOptionSets(List<OptionSetDto> optionSets) {
    return Optional.ofNullable(optionSets)
            .orElse(List.of())
            .stream()
            .map(this.optionSetMapper::from)
            .collect(Collectors.toList());
  }
}
