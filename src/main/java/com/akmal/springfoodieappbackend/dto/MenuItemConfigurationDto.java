package com.akmal.springfoodieappbackend.dto;

import java.util.List;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/01/2022 - 19:07
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record MenuItemConfigurationDto(long menuItemId,
                                       List<SelectedOption> selectedOptions) {

  /**
   * Utility DTO class which represents a selected item, since
   * the relationship between an option and option set is ManyToMany we have to know
   * to which set the option refers to.
   */
  public record SelectedOption(long optionId,
                               long optionSetId) {
  }
}
