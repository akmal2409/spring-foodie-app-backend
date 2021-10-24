package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 7:51 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Builder
public class Cart {
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = CascadeType.ALL)
  private final List<CartItem> cartItems;
  private final String userId;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
}
