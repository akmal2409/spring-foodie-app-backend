package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.CartDto;
import com.akmal.springfoodieappbackend.dto.CartItemDto;
import com.akmal.springfoodieappbackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * The class represents a set of endpoints needed to interact with the user's cart.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:28
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController(CartController.BASE_API)
@RequiredArgsConstructor
public class CartController {
  public static final String BASE_API = "/api/users/{userId}/carts";
  private final CartService cartService;

  @GetMapping
  public CartDto findByUserId(@PathVariable String userId) {
    return this.cartService.getCartForCurrentUser(userId);
  }

  @PostMapping
  public CartDto addItem(@RequestBody CartItemDto itemDto, @PathVariable String userId) {
    return this.cartService.addCartItem(itemDto, userId);
  }
}
