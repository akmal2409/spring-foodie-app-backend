package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
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
  private final LocalDateTime placedOn;
  @UpdateTimestamp
  private final LocalDateTime updatedOn;
  @Enumerated(EnumType.STRING)
  private final OrderStatus orderStatus;
  @Embedded
  private final Address address;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
  private final List<OrderLineItem> orderLineItems;
  private final BigDecimal totalPrice;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  public enum OrderStatus {
    AWAITING_PAYMENT, PROCESSING, PREPARING, DELIVERING, DELIVERED, CANCELED, REJECTED
  }
}
