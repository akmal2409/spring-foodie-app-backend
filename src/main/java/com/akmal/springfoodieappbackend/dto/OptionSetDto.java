package com.akmal.springfoodieappbackend.dto;

import java.util.List;

/**
 * DTO class implemented using Java Record Classes representing OptionSet entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:59 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record OptionSetDto(Long id,
                           String name,
                           int orderPosition,
                           int maximumOptionsSelected,
                           boolean exclusive,
                           boolean required,
                           String optionSetType,
                           long menuItemId,
                           List<OptionDto> options) {

  /**
   * The method creates an immutable copy of the instance object.
   * @param id of an entity.
   * @return {@link OptionSetDto} object with a new ID.
   */
  public OptionSetDto withId(Long id) {
    return new OptionSetDto(id, this.name, this.orderPosition,
            this.maximumOptionsSelected,
            this.exclusive,
            this.required,
            this.optionSetType,
            this.menuItemId,
            this.options);
  }
}
