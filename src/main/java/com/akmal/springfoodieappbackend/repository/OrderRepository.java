package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data Repository that represents the Order Entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:32 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
