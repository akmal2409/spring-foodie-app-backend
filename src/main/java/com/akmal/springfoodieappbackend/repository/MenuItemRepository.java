package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

  /**
   * Method is using custom JPQL Hibernate query, finds all {@link MenuItem} entities that have
   * thumbnail image or full image id's matching with the provided one.
   *
   * @param imageId of the image.
   * @return collection of {@link MenuItem} entities.
   */
  @Query("SELECT menuItem FROM MenuItem menuItem WHERE menuItem.thumbnailImage.id = ?1 OR menuItem.fullImage.id = ?1")
  Iterable<MenuItem> findAllByThumbnailImageOrFullImageId(String imageId);
}
