package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.CartDto;
import com.akmal.springfoodieappbackend.dto.CartItemDto;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.model.Cart;
import com.akmal.springfoodieappbackend.model.CartItem;
import com.akmal.springfoodieappbackend.model.MenuItem;
import com.akmal.springfoodieappbackend.repository.CartRepository;
import com.akmal.springfoodieappbackend.repository.MenuItemRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:39
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public abstract class CartMapper {
  final CartItemMapper cartItemMapper;
  final CartRepository cartRepository;
  final MenuItemRepository menuItemRepository;

  protected CartMapper(
      CartItemMapper cartItemMapper,
      CartRepository cartRepository,
      MenuItemRepository menuItemRepository) {
    this.cartItemMapper = cartItemMapper;
    this.cartRepository = cartRepository;
    this.menuItemRepository = menuItemRepository;
  }

  @Mapping(target = "cartItems", expression = "java(mapItemsToDto(cart.getCartItems()))")
  public abstract CartDto toDto(Cart cart);

  public List<CartItemDto> mapItemsToDto(List<CartItem> cartItems) {
    return Optional.ofNullable(cartItems).orElse(List.of()).stream()
        .map(this.cartItemMapper::toDto)
        .toList();
  }

  public List<CartItem> mapItems(List<CartItemDto> cartItems) {
    final List<CartItem> mappedItems = new ArrayList<>();

    for (CartItemDto cartItemDto : Optional.ofNullable(cartItems).orElse(List.of())) {
      final Cart cart =
          this.cartRepository
              .findById(cartItemDto.cartId())
              .orElseThrow(
                  () ->
                      new NotFoundException(
                          "Cart with ID " + cartItemDto.cartId() + " was not found"));
      final MenuItem menuItem =
          this.menuItemRepository
              .findById(cartItemDto.menuItemId())
              .orElseThrow(
                  () ->
                      new NotFoundException(
                          "Menu item with ID " + cartItemDto.menuItemId() + " was not found"));
      mappedItems.add(this.cartItemMapper.from(cartItemDto, cart, menuItem));
    }

    return mappedItems;
  }
}
