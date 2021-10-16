package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
  private final long menuItemId;

  @ManyToMany(fetch = FetchType.LAZY)
  private final Set<Option> selectedOptions = new HashSet<>();
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private final Order order;
}
