package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.OptionSet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data JPA repository that represents the OptionSet entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:30 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface OptionSetRepository extends JpaRepository<OptionSet, Long> {
}
