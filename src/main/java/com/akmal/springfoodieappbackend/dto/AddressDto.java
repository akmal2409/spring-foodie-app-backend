package com.akmal.springfoodieappbackend.dto;

/**
 * DTO class implemented using Java Record Classes representing Address entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:46 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record AddressDto(String country,
                         String city,
                         String postCode,
                         String street,
                         String addition,
                         String apartmentNumber,
                         LocationDto location) {
}
