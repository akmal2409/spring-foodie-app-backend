package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class Restaurant {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
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
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "restaurant")
  private final Set<OpeningTime> openingTimes = new HashSet<>();
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
  private final Set<Category> categories = new HashSet<>();
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "restaurant")
  private final List<Menu> menus = new ArrayList<>();

  /**
   * Enum representing the average cost of a restaurant.
   *
   * @author akmal
   * @version 1.0
   * @since 1.0
   */
  public enum PriceRange {
    AFFORDABLE, AVERAGE, EXPENSIVE
  }
}
