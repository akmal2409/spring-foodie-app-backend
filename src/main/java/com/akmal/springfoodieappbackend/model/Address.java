package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class encapsulates the address object. The logic was outsourced
 * to be further reused in multiple components of the system.
 * The class is embeddable and therefore, the Spring Data JPA will
 * flatten the object and include it as columns into the embedded object.
 * Fields that are not validated are optional.
 *
 * @author Akmal ALikhujaev
 * @version 1.0
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Embeddable
@Builder
public class Address {
  private final String country;
  @NotBlank
  @Size(min = 3)
  private final String city;
  @NotBlank
  @Size(min = 3)
  private final String postCode;
  @NotBlank
  private final String street;
  private final String addition;
  private final String apartmentNumber;
  @Embedded
  private final Location location;

  /**
   * Inner static class representing location object, needed
   * for geospatial queries.
   *
   * @author Akmal Alikhujaev
   * @version 1.0
   * @since 1.0
   */
  @Getter
  @NoArgsConstructor(force = true)
  @AllArgsConstructor
  @Embeddable
  @Builder
  public static class Location {
    private final double lat;
    private final double lon;
  }
}
