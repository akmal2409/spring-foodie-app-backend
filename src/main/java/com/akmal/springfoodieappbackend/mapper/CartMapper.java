package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.CartDto;
import com.akmal.springfoodieappbackend.dto.CartItemDto;
import com.akmal.springfoodieappbackend.model.Cart;
import com.akmal.springfoodieappbackend.model.CartItem;
import com.akmal.springfoodieappbackend.repository.CartRepository;
import com.akmal.springfoodieappbackend.repository.MenuItemRepository;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:39
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public abstract class CartMapper {
  @Autowired public CartItemMapper cartItemMapper;
  @Autowired public CartRepository cartRepository;
  @Autowired public MenuItemRepository menuItemRepository;

  @Mapping(target = "cartItems", expression = "java(mapItemsToDto(cart.getCartItems()))")
  public abstract CartDto toDto(Cart cart);

  public List<CartItemDto> mapItemsToDto(List<CartItem> cartItems) {
    return Optional.ofNullable(cartItems).orElse(List.of()).stream()
        .map(this.cartItemMapper::toDto)
        .toList();
  }
}
