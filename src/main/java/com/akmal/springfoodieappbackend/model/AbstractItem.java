package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * The class encapsulates the behaviour of an item
 * and extracts the common behaviour between OrderLineItem and a
 * CartItem, to avoid duplication.
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
public abstract sealed class AbstractItem permits OrderLineItem, CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Min(value = 1, message = "Quantity must be greater than 1")
  private final int quantity;
  private final BigDecimal totalPrice;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_item_id")
  private final MenuItem menuItem;
  @ManyToMany(fetch = FetchType.LAZY)
  private final Set<Option> selectedOptions = new HashSet<>();
}
