package com.akmal.springfoodieappbackend.dto;

/**
 * DTO class implemented using Java Record Classes representing Location entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:47 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record LocationDto(double lat,
                          double lon) {
}
