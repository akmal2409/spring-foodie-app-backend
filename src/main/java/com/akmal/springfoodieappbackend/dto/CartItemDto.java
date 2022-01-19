package com.akmal.springfoodieappbackend.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:34
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record CartItemDto(int quantity,
                          BigDecimal totalPrice,
                          long menuItemId,
                          List<OptionDto> selectedOptions,
                          Long id,
                          Long cartId) {
}
