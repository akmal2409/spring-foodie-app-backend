package com.akmal.springfoodieappbackend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * <h1>CartItem</h1>
 *
 * The class represents a single cart item in the cart.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:05 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@SuperBuilder
@With
public final class CartItem {
  @Min(value = 1, message = "Quantity must be greater than 1")
  private final int quantity;

  private final BigDecimal totalPrice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_item_id")
  private final MenuItem menuItem;

  @ManyToMany(fetch = FetchType.LAZY)
  @Builder.Default
  private final List<Option> selectedOptions = new ArrayList<>();

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", referencedColumnName = "id")
  private final Cart cart;
}
