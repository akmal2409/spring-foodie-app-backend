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
 * The class {@code OrderLineItem} represents a single item in the user's order. It extends {@link
 * AbstractItem} class in order to avoid code duplication between the {@code OrderLineItem} and the
 * {@link CartItem} class.
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
public final class OrderLineItem extends AbstractItem {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private final Order order;
}
