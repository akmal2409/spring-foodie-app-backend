package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
