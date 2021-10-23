package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring data JPA repository to persist and manage restaurant entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/10/2021 - 8:16 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

  /**
   * Method that is using custom JPQL hibernate query, it is discouraged to use native queries
   * directly.
   *
   * @param country  String - Restaurant Country
   * @param city     String - Restaurant City
   * @param pageable Pageable - Spring Data Jpa Pageable object, defines the page, size and the
   *                 sorting direction needed for pagination.
   * @return page - Page object containing content, number of the page, size, totalPages etc.
   */
  @Query("SELECT res FROM Restaurant res WHERE res.address.country = ?1 AND res.address.city = ?2")
  Page<Restaurant> findAllByCountryAndCity(String country, String city, Pageable pageable);
}
