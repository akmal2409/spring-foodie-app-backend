package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

/**
 * The class encapsulates the behaviour of an item
 * and extracts the common behaviour between OrderLineItem and a
 * CartItem, to avoid duplication.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 7:59 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@MappedSuperclass
@SuperBuilder
public abstract sealed class AbstractItem permits OrderLineItem, CartItem {
  @Min(value = 1, message = "Quantity must be greater than 1")
  private final int quantity;
  private final BigDecimal totalPrice;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_item_id")
  private final MenuItem menuItem;
  @ManyToMany(fetch = FetchType.LAZY)
  private final List<Option> selectedOptions;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
}
