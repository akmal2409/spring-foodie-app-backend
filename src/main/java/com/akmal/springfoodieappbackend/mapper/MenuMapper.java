package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.CategoryDto;
import com.akmal.springfoodieappbackend.dto.MenuDto;
import com.akmal.springfoodieappbackend.dto.MenuItemDto;
import com.akmal.springfoodieappbackend.model.Category;
import com.akmal.springfoodieappbackend.model.Menu;
import com.akmal.springfoodieappbackend.model.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class represents a contract based on which Mapstruct library generates the
 * mapper class at runtime. Converts Menu Object to DTO and vice versa.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:48 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class MenuMapper {
  @Autowired
  private CategoryMapper categoryMapper;
  @Autowired
  private MenuItemMapper menuItemMapper;

  @Mapping(target = "category", expression = "java(mapCategoryToDto(menu.getCategory()))")
  @Mapping(target = "restaurantId", source = "restaurant.id")
  @Mapping(target = "menuItems", expression = "java(mapMenuItemsToDto(menu.getMenuItems()))")
  public abstract MenuDto toDto(Menu menu);

  @Mapping(target = "category", expression = "java(mapToCategory(menuDto.category()))")
  @Mapping(target = "restaurant", ignore = true)
  @Mapping(target = "menuItems", expression = "java(mapToMenuItems(menuDto.menuItems()))")
  public abstract Menu from(MenuDto menuDto);

  protected CategoryDto mapCategoryToDto(Category category) {
    return this.categoryMapper.toDto(category);
  }

  protected Category mapToCategory(CategoryDto category) {
    return this.categoryMapper.from(category);
  }

  protected List<MenuItemDto> mapMenuItemsToDto(List<MenuItem> menuItems) {
    return Optional.ofNullable(menuItems)
            .orElse(List.of())
            .stream()
            .map(this.menuItemMapper::toDto)
            .toList();
  }

  protected List<MenuItem> mapToMenuItems(List<MenuItemDto> menuItems) {
    return Optional.ofNullable(menuItems)
            .orElse(List.of())
            .stream()
            .map(this.menuItemMapper::from)
            .toList();
  }
}
