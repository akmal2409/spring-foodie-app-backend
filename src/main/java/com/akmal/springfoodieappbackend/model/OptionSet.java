package com.akmal.springfoodieappbackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * <h1>OptionSet</h1>
 *
 * Class represents a set of options that can be added to the menu item. <br>
 * One important thing to know is that if {@code maximumOptionsSelected} is -1 <br>
 * then there is no limit in selecting the options from the set. <br>
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
@With
public class OptionSet {
  @NotBlank(message = "Name is required")
  private final String name;

  @Min(value = 0)
  private final int orderPosition;

  @Min(value = -1, message = "Maximum option selected can be ether -1 or greater")
  private final int maximumOptionsSelected;

  private final boolean exclusive;
  private final boolean required;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
  private final MenuItem menuItem;

  @Enumerated(EnumType.STRING)
  private final OptionSetType optionSetType;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(
      name = "option_set_options",
      joinColumns = @JoinColumn(name = "option_set_id"),
      inverseJoinColumns = @JoinColumn(name = "option_id"))
  @Builder.Default
  private List<Option> options = new ArrayList<>();

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public enum OptionSetType {
    BEVERAGE("Beverage"),
    TOPPING("Topping"),
    EXTRA("Extra"),
    SAUCE("Sauce"),
    DESSERT("Dessert"),
    SIZE("Size");

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
