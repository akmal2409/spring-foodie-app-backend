package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.Restaurant;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
   * @param country String - Restaurant Country
   * @param city String - Restaurant City
   * @param pageable Pageable - Spring Data Jpa Pageable object, defines the page, size and the
   *     sorting direction needed for pagination.
   * @return page - Page object containing content, number of the page, size, totalPages etc.
   */
  @Query(
      value =
          "SELECT res FROM Restaurant res WHERE UPPER(TRIM(res.address.country)) = UPPER(TRIM(:country)) "
              + "AND UPPER(TRIM(res.address.city)) = UPPER(TRIM(:city)) "
              + "ORDER BY res.rating DESC")
  Page<Restaurant> findAllByCountryAndCity(
      Pageable pageable, @Param("country") String country, @Param("city") String city);

  /**
   * Method is using custom JPQL Hibernate query, finds all restaurants that have thumbnail image or
   * full image id's matching with the provided one.
   *
   * @param imageId of the image.
   * @return collection of {@link Restaurant} entities.
   */
  @Query(
      value =
          "SELECT res FROM Restaurant res WHERE res.thumbnailImage.id = ?1 OR res.fullImage.id = ?1")
  Iterable<Restaurant> findAllByThumbnailImageOrFullImageId(String imageId);

  List<Restaurant> findAllByUpdatedOnAfter(LocalDateTime from);
}
