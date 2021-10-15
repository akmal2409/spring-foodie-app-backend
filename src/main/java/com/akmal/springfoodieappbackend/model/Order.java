package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Akmal Alikhujaev
 * @created 15/10/2021 - 9:34 PM
 * @project Spring Foodie App Backend
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private final String userId;
  @CreationTimestamp
  private final LocalDateTime placedOn;
  @UpdateTimestamp
  private final LocalDateTime updatedOn;
  @Enumerated(EnumType.STRING)
  private final OrderStatus orderStatus;

  public enum OrderStatus {
    AWAITING_PAYMENT, PROCESSING, PREPARING, DELIVERING, DELIVERED, CANCELED, REJECTED;
  }
}
