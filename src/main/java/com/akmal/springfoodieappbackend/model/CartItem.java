package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * <h1>CartItem</h1>
 * The class represents a single cart item in the cart, it
 * extends the {@link AbstractItem} abstract class that encapsulates common attributes
 * between an order line item and the cart item.
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
public final class CartItem extends AbstractItem {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  private final Cart cart;
}
