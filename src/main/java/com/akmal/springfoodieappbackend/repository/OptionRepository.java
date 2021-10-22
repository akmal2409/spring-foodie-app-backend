package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data Jpa repository that represents Option Entity.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:31 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface OptionRepository extends JpaRepository<Option, Long> {
}
