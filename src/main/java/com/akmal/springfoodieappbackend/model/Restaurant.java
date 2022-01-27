package com.akmal.springfoodieappbackend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.UpdateTimestamp;

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

  @Embedded private final Address address;
  private final int averageDeliveryTime;
  private final BigDecimal deliveryCost;
  private final double minimumOrderValue;
  private final String ownerId;

  @DecimalMin(value = "0.0")
  @DecimalMax(value = "5.0")
  private final double rating;

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      mappedBy = "restaurant",
      orphanRemoval = true)
  @OrderBy(value = "day asc")
  @Builder.Default
  private List<OpeningTime> openingTimes = new ArrayList<>();

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REFRESH, CascadeType.MERGE})
  @Builder.Default
  private List<Category> categories = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY)
  private Image thumbnailImage;

  @OneToOne(fetch = FetchType.LAZY)
  private Image fullImage;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @UpdateTimestamp private LocalDateTime updatedOn;

  /**
   * <strong>addOpeningTime(OpeningTime openingTime)</strong> is a helper method that enables the
   * client to synchronize the both sides of the @OneToMany relationship. The owning side of the
   * relationship is the {@link OpeningTime} class and therefore, it should manage the persistence
   * of the restaurant by itself.
   *
   * @param openingTime - object representing opening time of a restaurant
   * @return immutable copy of the {@link OpeningTime} instance with the restaurant reference
   */
  public OpeningTime addOpeningTime(OpeningTime openingTime) {
    final var openingTimeWithRestaurant = openingTime.withRestaurant(this);
    this.openingTimes.add(openingTimeWithRestaurant);

    return openingTimeWithRestaurant;
  }
}
