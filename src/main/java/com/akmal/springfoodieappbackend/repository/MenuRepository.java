package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data Jpa repository that represents the Menu Entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:26 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

  Page<Menu> findAllByRestaurantId(long restaurantId);
}
