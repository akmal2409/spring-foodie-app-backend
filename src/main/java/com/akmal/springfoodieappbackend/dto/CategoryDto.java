package com.akmal.springfoodieappbackend.dto;

/**
 * DTO class implemented using Java Record Classes representing CategoryDto entity
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:54 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record CategoryDto(long id,
                          String name,
                          String description) {
}
