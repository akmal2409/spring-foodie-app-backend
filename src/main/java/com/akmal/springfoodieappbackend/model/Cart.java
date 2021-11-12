package com.akmal.springfoodieappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Cart</h1>
 * The class represents a cart, which is a collection of the cart items.
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
  private List<CartItem> cartItems = new ArrayList<>();
  private final String userId;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * <strong>addCartItem(CartItem cartItem)</strong> is a helper method that enables the client
   * to synchronize the both sides of the @OneToMany relationship.
   * The owning side of the relationship is the {@link CartItem} class
   * and therefore, it should manage the persistence of the cart by itself.
   * @param cartItem - object representing a single cart item
   * @return immutable copy of the {@link CartItem} instance with the cart reference
   */
  public CartItem addCartItem(CartItem cartItem) {
    final var cartItemWithCart = cartItem.withCart(this);
    this.cartItems.add(cartItemWithCart);
    return cartItemWithCart;
  }
}
