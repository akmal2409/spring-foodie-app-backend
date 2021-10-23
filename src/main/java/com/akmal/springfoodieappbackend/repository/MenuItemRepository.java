package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data JPA repository for the MenuItem entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:24 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
