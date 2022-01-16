package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

/**
 * The class {@code OrderLineItem} represents a single item in the user's order. It contains a
 * snapshot data at the time of the purchase, however, it also contains references to the original
 * {@link Menu} and {@link MenuItem} entities.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/10/2021 - 8:44 AM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
@With
public final class OrderLineItem {
  @Min(value = 1, message = "Quantity must be greater than 1")
  private final int quantity;

  private final BigDecimal totalPrice;
  private final BigDecimal basePrice;
  private final Long originalMenuItemId;
  private final Long originalMenuId;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private final Order order;

  @OneToMany(
      fetch = FetchType.LAZY,
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      mappedBy = "orderLineItem")
  private List<OrderLineItemOption> selectedOptions;
}
