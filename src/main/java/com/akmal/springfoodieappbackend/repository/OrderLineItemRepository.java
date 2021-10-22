package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data Jpa repository and represents the OrderLineItem Entity.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:32 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {
}
