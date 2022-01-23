package com.akmal.springfoodieappbackend.controller;

import com.akmal.springfoodieappbackend.dto.CartDto;
import com.akmal.springfoodieappbackend.dto.ModifyCartRequest;
import com.akmal.springfoodieappbackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class represents a set of endpoints needed to interact with the user's cart.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:28
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestController
@RequestMapping(CartController.BASE_API)
@RequiredArgsConstructor
public class CartController {
  public static final String BASE_API = "/api/users/{userId}/carts";
  private final CartService cartService;

  @GetMapping
  public CartDto findByUserId(@PathVariable String userId) {
    return this.cartService.getCartForCurrentUser(userId);
  }

  @PostMapping
  public CartDto addItem(
      @RequestBody ModifyCartRequest cartItemRequest, @PathVariable String userId) {
    return this.cartService.addCartItem(cartItemRequest, userId);
  }

  @DeleteMapping("/{cartItemId}")
  public CartDto removeItem(@PathVariable String userId, @PathVariable Long cartItemId) {
    return this.cartService.removeCartItem(userId, cartItemId);
  }
}
