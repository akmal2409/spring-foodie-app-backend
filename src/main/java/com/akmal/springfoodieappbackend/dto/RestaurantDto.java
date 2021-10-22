package com.akmal.springfoodieappbackend.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * DTO class implemented using Java Record Classes representing Restaurant entity
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:44 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record RestaurantDto(long id,
                            String name,
                            String phone,
                            AddressDto address,
                            int averageDeliveryTime,
                            BigDecimal deliveryCost,
                            double minimumOrderValue,
                            String priceRange,
                            double rating,
                            Set<OpeningTimeDto> openingTimes,
                            Set<CategoryDto> categories,
                            List<MenuDto> menus) {
}
