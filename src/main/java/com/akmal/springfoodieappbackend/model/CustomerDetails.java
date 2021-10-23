package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Class represent the Customer details model.
 * {@code userId} field is mapped from the Okta user (The Authentication provider)
 *
 * @author akmal
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
public class CustomerDetails {
  @Embedded
  private final Address address;
  @NotBlank(message = "First Name is required")
  private final String firstName;
  @NotBlank(message = "Last Name is required")
  private final String lastName;
  @NotBlank(message = "Phone number is required")
  private final String phoneNumber;
  @Id
  private String userId;
}
