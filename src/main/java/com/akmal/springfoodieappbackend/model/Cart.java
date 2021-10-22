package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
  private final List<CartItem> cartItems = new ArrayList<>();
  private final String userId;
}
