package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public final class OrderLineItem extends AbstractItem {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private final Order order;
}
