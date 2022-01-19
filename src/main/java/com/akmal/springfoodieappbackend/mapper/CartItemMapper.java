package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.CartItemDto;
import com.akmal.springfoodieappbackend.dto.OptionDto;
import com.akmal.springfoodieappbackend.model.Cart;
import com.akmal.springfoodieappbackend.model.CartItem;
import com.akmal.springfoodieappbackend.model.MenuItem;
import com.akmal.springfoodieappbackend.model.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;
import java.util.Optional;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:39
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class CartItemMapper {
  final OptionMapper optionMapper;

  protected CartItemMapper(OptionMapper optionMapper) {
    this.optionMapper = optionMapper;
  }

  @Mapping(target = "menuItemId", source = "cartItem.menuItem.id")
  @Mapping(target = "cartId", source = "cartItem.cart.id")
  @Mapping(
      target = "selectedOptions",
      expression = "java(mapOptionsToDto(cartItem.selectedOptions))")
  public abstract CartItemDto toDto(CartItem cartItem);

  @Mapping(target = "cart", source = "cart")
  @Mapping(target = "menuItem", source = "menuItem")
  @Mapping(
      target = "selectedOptions",
      expression = "java(mapOptions(cartItemDto.selectedOptions()))")
  public abstract CartItem from(CartItemDto cartItemDto, Cart cart, MenuItem menuItem);

  public List<OptionDto> mapOptionsToDto(List<Option> options) {
    return Optional.ofNullable(options).orElse(List.of()).stream()
        .map(this.optionMapper::toDto)
        .toList();
  }

  public List<Option> mapOptions(List<OptionDto> options) {
    return Optional.ofNullable(options).orElse(List.of()).stream()
        .map(this.optionMapper::from)
        .toList();
  }
}
