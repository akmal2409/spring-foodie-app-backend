package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data Jpa Repository that represents Category Entity
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:26 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
