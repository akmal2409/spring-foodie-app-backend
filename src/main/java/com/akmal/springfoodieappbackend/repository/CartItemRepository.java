package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data Jpa repository that represents CartItem Entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:33 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {}
