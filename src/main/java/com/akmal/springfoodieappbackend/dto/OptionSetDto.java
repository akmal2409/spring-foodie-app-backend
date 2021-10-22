package com.akmal.springfoodieappbackend.dto;

import java.util.Set;

/**
 * DTO class implemented using Java Record Classes representing OptionSet entity
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:59 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record OptionSetDto(long id,
                           String name,
                           int orderPosition,
                           int maximumOptionsSelected,
                           boolean exclusive,
                           boolean required,
                           String optionSetType,
                           Set<OptionDto> options) {
}
