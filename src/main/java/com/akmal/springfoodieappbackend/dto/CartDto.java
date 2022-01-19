package com.akmal.springfoodieappbackend.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * The class represents a data transfer object of {@link com.akmal.springfoodieappbackend.model.Cart}
 * and hides certain fields from the end client.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:33
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record CartDto(String userId,
                      Long id,
                      BigDecimal totalPrice,
                      List<CartItemDto> cartItems) {
}
