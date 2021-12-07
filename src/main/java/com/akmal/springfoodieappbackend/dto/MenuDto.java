package com.akmal.springfoodieappbackend.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO class implemented using Java Record Classes representing Menu entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:56 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record MenuDto(Long id,
                      @NotEmpty(message = "Name is required") String name,
                      @NotNull(message = "Category is required") CategoryDto category,
                      long restaurantId) {
}
