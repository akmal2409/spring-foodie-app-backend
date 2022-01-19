package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The class is a Spring Data Jpa repository that represents the Cart Entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:33 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findCartByUserId(String userId);
}
