package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.ImageDto;
import com.akmal.springfoodieappbackend.dto.MenuItemDto;
import com.akmal.springfoodieappbackend.dto.OptionSetDto;
import com.akmal.springfoodieappbackend.model.Image;
import com.akmal.springfoodieappbackend.model.MenuItem;
import com.akmal.springfoodieappbackend.model.OptionSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Class represents a contract based on which Mapstruct library generates the mapper class at
 * runtime. Converts MenuItem Object to DTO and vice versa.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:55 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class MenuItemMapper {
  @Autowired private OptionSetMapper optionSetMapper;

  @Mapping(target = "menuId", source = "menuItem.menu.id")
  @Mapping(target = "optionSets", expression = "java(mapOptionSetsToDto(menuItem.getOptionSets()))")
  @Mapping(
      target = "thumbnailImage",
      expression = "java(mapImageToDto(menuItem.getThumbnailImage()))")
  @Mapping(target = "fullImage", expression = "java(mapImageToDto(menuItem.getFullImage()))")
  public abstract MenuItemDto toDto(MenuItem menuItem);

  @Mapping(target = "menu", ignore = true)
  @Mapping(target = "optionSets", expression = "java(mapToOptionSets(menuItemDto.optionSets()))")
  @Mapping(target = "thumbnailImage", expression = "java(mapToImage(menuItemDto.thumbnailImage()))")
  @Mapping(target = "fullImage", expression = "java(mapToImage(menuItemDto.fullImage()))")
  public abstract MenuItem from(MenuItemDto menuItemDto);

  protected List<OptionSetDto> mapOptionSetsToDto(List<OptionSet> optionSets) {
    return Optional.ofNullable(optionSets).orElse(List.of()).stream()
        .map(this.optionSetMapper::toDto)
        .toList();
  }

  protected List<OptionSet> mapToOptionSets(List<OptionSetDto> optionSets) {
    return Optional.ofNullable(optionSets).orElse(List.of()).stream()
        .map(this.optionSetMapper::from)
        .toList();
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
