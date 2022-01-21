package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 20:50
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DeliveryAddress {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private final String label;
  private final String country;
  private final String city;
  private final String postCode;
  private final String street;

  @Enumerated(EnumType.STRING)
  private final AddressType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_details_id", referencedColumnName = "user_id")
  private CustomerDetails customerDetails;

  public enum AddressType {
    HOME,
    WORK,
    OTHER;
  }
}
