package com.akmal.springfoodieappbackend.dto;

/**
 * The class represents an action DTO that is used to add the CartItem into the cart.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 19/01/2022 - 19:27
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record ModifyCartRequest(int quantity,
                                MenuItemConfigurationDto menuItemConfig,
                                long cartId) {
}
