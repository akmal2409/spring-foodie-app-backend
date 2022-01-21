package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represent the Customer details model. {@code userId} field is mapped from the Okta user
 * (The Authentication provider)
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
  @NotBlank(message = "First Name is required")
  private final String firstName;

  @NotBlank(message = "Last Name is required")
  private final String lastName;

  @NotBlank(message = "Phone number is required")
  private final String phoneNumber;

  @Id
  @Column(name = "user_id")
  private String userId;

  @OneToMany(
      fetch = FetchType.LAZY,
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      mappedBy = "customerDetails")
  @Builder.Default
  private List<DeliveryAddress> deliveryAddresses = new ArrayList<>();
}
