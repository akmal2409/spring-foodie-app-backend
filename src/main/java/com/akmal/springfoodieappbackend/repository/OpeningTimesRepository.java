package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.OpeningTimes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The class is a Spring Data Jpa repository for the OpeningTimes entity.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:24 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface OpeningTimesRepository extends JpaRepository<OpeningTimes, Long> {
}
