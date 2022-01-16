package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The class represents a snapshot of {@link Option} at the moment of purchase of the user. It also
 * contains references back to the original object.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:18
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class OrderLineItemOption {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private final String name;
  private final BigDecimal price;
  private final Long originalOptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_line_item_id", referencedColumnName = "id")
  private OrderLineItem orderLineItem;
}
