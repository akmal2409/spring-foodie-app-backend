package com.akmal.springfoodieappbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Order} class represents a single user's order consisting
 * of the {@link OrderLineItem} class's instances. Encapsulates all the data
 * and logic needed to complete the order from a restaurant.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 15/10/2021 - 9:34 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "Orders")
@Builder
public class Order {
  private final String userId;
  @CreationTimestamp
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private final LocalDateTime placedOn;
  @UpdateTimestamp
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private final LocalDateTime updatedOn;
  @Enumerated(EnumType.STRING)
  private final OrderStatus orderStatus;
  @Embedded
  private final Address address;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
  private List<OrderLineItem> orderLineItems = new ArrayList<>();
  private final BigDecimal totalPrice;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  public enum OrderStatus {
    AWAITING_PAYMENT, PROCESSING, PREPARING, DELIVERING, DELIVERED, CANCELED, REJECTED
  }

  /**
   * <strong>addOrderLineItems(OrderLineItem orderLineItem)</strong> is a helper method that enables the client
   * to synchronize the both sides of the @OneToMany relationship.
   * The owning side of the relationship is the {@link OrderLineItem} class
   * and therefore, it should manage the persistence of an order by itself.
   * @param orderLineItem - object representing a single item in the order
   * @return immutable copy of the {@link OrderLineItem} instance with the order reference.
   */
  public OrderLineItem addOrderLineItem(OrderLineItem orderLineItem) {
    final var itemWithOrder = orderLineItem.withOrder(this);
    this.orderLineItems.add(itemWithOrder);
    return itemWithOrder;
  }
}
