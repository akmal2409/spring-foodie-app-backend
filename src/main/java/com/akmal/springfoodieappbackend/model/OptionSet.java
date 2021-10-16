package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Class represents a set of options that can be added to the menu item.
 * One important thing to know is that if {@code maximumOptionsSelected} is -1
 * then there is no limit in selecting the options from the set.
 * If set is {@code exclusive} then only one option can be selected, else multiple.
 * @author Akmal ALikhujaev
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OptionSet {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @NotBlank(message = "Name is required")
  private final String name;
  @Min(value = 0)
  private final int orderPosition;
  @Min(value = -1, message = "Maximum option selected can be ether -1 or greater")
  private final int maximumOptionsSelected;
  private final boolean exclusive;
  private final boolean required;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "option_set_options",
    joinColumns = @JoinColumn(name = "option_set_id"),
    inverseJoinColumns = @JoinColumn(name = "option_id"))
  private final Set<Option> options = new HashSet<>();
  @Enumerated(EnumType.STRING)
  private final OptionSetType optionSetType;

  public enum OptionSetType {
    BEVERAGE, TOPPING, EXTRA, SAUCE, ICE_CREAM;
  }
}
