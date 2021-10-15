package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Class represents a single Menu Item which contains available for the
 * selection option sets.
 * @author Akmal ALikhujaev
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MenuItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotBlank(message = "Name is required")
  private final String name;
  @DecimalMin(value = "0.0", message = "Base price must be greater than 0")
  private final BigDecimal basePrice;
  @ManyToMany(fetch = FetchType.LAZY)
  private final Set<OptionSet> optionSet = new HashSet<>();
}
