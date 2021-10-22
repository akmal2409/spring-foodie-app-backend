package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Akmal Alikhujaev
 * @created 16/10/2021 - 8:44 AM
 * @project Spring Foodie App Backend
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderLineItem {
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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private final Order order;
}
