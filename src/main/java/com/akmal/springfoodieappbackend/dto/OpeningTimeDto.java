package com.akmal.springfoodieappbackend.dto;

import java.time.LocalTime;

/**
 * DTO class implemented using Java Record Classes representing OpeningTime entity
 * Day is an integer from 1 to 7 where 1 is Sunday.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:50 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record OpeningTimeDto(long id,
                             int day,
                             LocalTime openFrom,
                             LocalTime openTill,
                             long restaurantId) {
}
