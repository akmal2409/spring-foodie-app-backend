package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.CartItemDto;
import com.akmal.springfoodieappbackend.dto.OptionDto;
import com.akmal.springfoodieappbackend.model.CartItem;
import com.akmal.springfoodieappbackend.model.Option;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:39
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class CartItemMapper {
  @Autowired OptionMapper optionMapper;

  @Mapping(target = "menuItemId", source = "cartItem.menuItem.id")
  @Mapping(target = "cartId", source = "cartItem.cart.id")
  @Mapping(
      target = "selectedOptions",
      expression = "java(mapOptionsToDto(cartItem.getSelectedOptions()))")
  public abstract CartItemDto toDto(CartItem cartItem);

  public List<OptionDto> mapOptionsToDto(List<Option> options) {
    return Optional.ofNullable(options).orElse(List.of()).stream()
        .map(this.optionMapper::toDto)
        .toList();
  }
}
