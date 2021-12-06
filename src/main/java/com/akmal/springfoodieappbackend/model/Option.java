package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Class represents option in an option set.
 * Option may relate to multiple option sets to reduce the overhead
 * of storing unnecessary data and reuse the values to improve user
 * experience.
 *
 * @author Akmal ALikhujaev
 * @version 1.0
 * @created 15/10/2021 - 9:21 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "menu_item_options")
@Builder
public class Option {
  @NotBlank(message = "Name is required")
  private final String name;
  @DecimalMin(value = "0.0", message = "Price is required")
  private final BigDecimal price;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
}
