package com.akmal.springfoodieappbackend.model;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the data model of a restaurant.
 *
 * @author Akmal ALikhujaev
 * @version 1.0
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Builder(toBuilder = true)
@With
public class Restaurant {
  @NotBlank(message = "Name is required")
  private final String name;
  @NotBlank(message = "Phone is required")
  private final String phone;
  @Embedded
  private final Address address;
  private final int averageDeliveryTime;
  private final BigDecimal deliveryCost;
  private final double minimumOrderValue;
  @Enumerated(value = EnumType.STRING)
  private final PriceRange priceRange;
  @DecimalMin(value = "0.0")
  @DecimalMax(value = "5.0")
  private final double rating;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "restaurant", orphanRemoval = true)
  private final List<OpeningTime> openingTimes;
  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
  private final List<Category> categories;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "restaurant", orphanRemoval = true)
  private final List<Menu> menus;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * Enum representing the average cost of a restaurant.
   *
   * @author akmal
   * @version 1.0
   * @since 1.0
   */
  public enum PriceRange {
    AFFORDABLE("Affordable"), AVERAGE("Average"), EXPENSIVE("Expensive");

    private final String type;

    PriceRange(String type) {
      this.type = type;
    }

    public static PriceRange from(String type) {
      return Arrays.stream(PriceRange.values())
              .filter(code -> code.type.equals(type))
              .findFirst()
              .orElse(null);
    }

    public String getType() {
      return this.type;
    }
  }
}
