package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Restaurant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private final Set<OpeningTimes> openingTimes = new HashSet<>();
  @ManyToMany(fetch = FetchType.LAZY)
  private final Set<Category> categories = new HashSet<>();

  public enum PriceRange {
    AFFORDABLE, AVERAGE, EXPENSIVE
  }
}
