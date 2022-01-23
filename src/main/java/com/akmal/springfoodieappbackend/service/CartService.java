package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.dto.CartDto;
import com.akmal.springfoodieappbackend.dto.ModifyCartRequest;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import com.akmal.springfoodieappbackend.mapper.CartItemMapper;
import com.akmal.springfoodieappbackend.mapper.CartMapper;
import com.akmal.springfoodieappbackend.model.Cart;
import com.akmal.springfoodieappbackend.model.CartItem;
import com.akmal.springfoodieappbackend.model.MenuItem;
import com.akmal.springfoodieappbackend.model.Option;
import com.akmal.springfoodieappbackend.repository.CartItemRepository;
import com.akmal.springfoodieappbackend.repository.CartRepository;
import com.akmal.springfoodieappbackend.repository.MenuItemRepository;
import com.akmal.springfoodieappbackend.shared.database.TransactionRunner;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class represents a service layer that manages the business logic for {@link
 * com.akmal.springfoodieappbackend.model.Cart} and {@link
 * com.akmal.springfoodieappbackend.model.CartItem} objects.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 16/01/2022 - 21:29
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final TransactionRunner transactionRunner;
  private final CartMapper cartMapper;
  private final CartItemMapper cartItemMapper;
  private final MenuItemRepository menuItemRepository;
  private final MenuItemService menuItemService;

  /**
   * The method is responsible for returning the cart for the current user. If the cart does not
   * exist at the moment of fetching, usually that is the first time when the user is accessing the
   * cart, the instance of it is automatically created and associated with a user.
   *
   * @return {@link CartDto} with modified item list.
   */
  @Transactional
  @PreAuthorize(value = "@userService.currentUser.userId().equals(#userId)")
  public CartDto getCartForCurrentUser(String userId) {
    final Cart cart =
        this.transactionRunner.runInTransaction(() -> this.findCartByUserIdOrDefault(userId));

    return this.cartMapper.toDto(cart);
  }

  @Transactional
  @PreAuthorize(value = "@userService.currentUser.userId().equals(#userId)")
  public CartDto addCartItem(ModifyCartRequest cartRequest, String userId) {
    final Cart cart =
        this.transactionRunner.runInTransaction(() -> this.findCartByUserIdOrDefault(userId));

    final List<Option> options =
        this.menuItemService.validateMenuItemConfiguration(cartRequest.menuItemConfig());

    final var menuItem =
        this.menuItemRepository
            .findById(cartRequest.menuItemConfig().menuItemId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            "Menu item with ID %d was not found",
                            cartRequest.menuItemConfig().menuItemId())));
    final var totalPrice =
        this.transactionRunner.runInTransaction(
            () -> this.calculateItemTotalPrice(menuItem, options));
    final var cartItem =
        CartItem.builder()
            .quantity(cartRequest.quantity())
            .menuItem(menuItem)
            .cart(cart)
            .selectedOptions(options)
            .totalPrice(totalPrice)
            .build();

    cart.addCartItem(cartItem);

    final var recalculatedCart =
        cart.toBuilder()
            .totalPrice(
                this.transactionRunner.runInTransaction(() -> this.calculateCartTotalPrice(cart)))
            .build();

    final var savedCart = this.cartRepository.save(recalculatedCart);

    return this.cartMapper.toDto(savedCart);
  }

  private BigDecimal calculateItemTotalPrice(MenuItem menuItem, List<Option> selectedOptions) {
    final BigDecimal basePrice =
        Optional.ofNullable(menuItem.getBasePrice()).orElse(BigDecimal.ZERO);

    return selectedOptions.stream().map(Option::getPrice).reduce(basePrice, BigDecimal::add);
  }

  private BigDecimal calculateCartTotalPrice(Cart cart) {
    return cart.getCartItems().stream()
        .map(CartItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /**
   * The method is responsible for fetching the existing cart or creating a new one based on the
   * state of the database.
   *
   * @param userId of the cart owner
   * @return raw {@link Cart} entity object.
   */
  private Cart findCartByUserIdOrDefault(String userId) {
    final Optional<Cart> cartOptional = this.cartRepository.findCartByUserId(userId);

    Cart cart = null;

    if (cartOptional.isEmpty()) {
      cart = this.transactionRunner.runInTransaction(() -> this.createCart(userId));
    } else {
      cart = cartOptional.get();
    }
    return cart;
  }

  private Cart createCart(String userId) {
    final var cart = Cart.newInstance(userId);
    return this.cartRepository.save(cart);
  }
}
