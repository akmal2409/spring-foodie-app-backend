package com.akmal.springfoodieappbackend.dto;

import java.util.List;

/**
 * DTO class implemented using Java Record Classes representing Menu entity
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:56 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record MenuDto(long id,
                      String name,
                      CategoryDto categoryDto,
                      long restaurantId,
                      List<MenuItemDto> menuItems) {
}
