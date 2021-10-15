package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class encapsulates the address object. The logic was outsourced
 * to be further reused in multiple components of the system.
 * The class is embeddable and therefore, the Spring Data JPA will
 * flatten the object and include it as columns into the embedded object.
 * Fields that are not validated are optional.
 * @author Akmal ALikhujaev
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @version 1.0
 * @since 1.0
 */
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Embeddable
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
}
