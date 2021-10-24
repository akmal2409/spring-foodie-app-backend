package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents a set of options that can be added to the menu item.
 * One important thing to know is that if {@code maximumOptionsSelected} is -1
 * then there is no limit in selecting the options from the set.
 * If set is {@code exclusive} then only one option can be selected, else multiple.
 *
 * @author Akmal ALikhujaev
 * @version 1.0
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class OptionSet {
  @NotBlank(message = "Name is required")
  private final String name;
  @Min(value = 0)
  private final int orderPosition;
  @Min(value = -1, message = "Maximum option selected can be ether -1 or greater")
  private final int maximumOptionsSelected;
  private final boolean exclusive;
  private final boolean required;
  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH})
  @JoinTable(name = "option_set_options",
          joinColumns = @JoinColumn(name = "option_set_id"),
          inverseJoinColumns = @JoinColumn(name = "option_id"))
  private final List<Option> options;
  @Enumerated(EnumType.STRING)
  private final OptionSetType optionSetType;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  public enum OptionSetType {
    BEVERAGE("Beverage"), TOPPING("Topping"), EXTRA("Extra"), SAUCE("Sauce"), DESSERT("Dessert");

    private final String type;

    OptionSetType(String type) {
      this.type = type;
    }

    public static OptionSetType from(String type) {
      return Arrays.stream(OptionSetType.values())
              .filter(code -> code.type.equals(type))
              .findFirst()
              .orElse(null);
    }

    public String getType() {
      return this.type;
    }
  }
}
