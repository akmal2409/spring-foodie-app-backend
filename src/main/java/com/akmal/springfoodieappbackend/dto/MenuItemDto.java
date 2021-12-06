package com.akmal.springfoodieappbackend.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO class implemented using Java Record Classes representing MenuItem entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:58 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record MenuItemDto(Long id,
                          String name,
                          BigDecimal basePrice,
                          long menuId,
                          List<OptionSetDto> optionSets,
                          ImageDto thumbnailImage,
                          ImageDto fullImage) {
}
