package com.akmal.springfoodieappbackend.dto;

import java.math.BigDecimal;

/**
 * DTO class implemented using Java Record Classes representing Option entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 9:00 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record OptionDto(Long id,
                        String name,
                        BigDecimal price) {
}
