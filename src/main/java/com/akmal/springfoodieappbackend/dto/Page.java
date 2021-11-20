package com.akmal.springfoodieappbackend.dto;

import java.util.Objects;

/**
 * The record class represents the Page response object needed for client side pagination.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 4:39 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record Page<T>(Iterable<T> content,
                      int currentPage,
                      long totalItems,
                      int totalPages) {

  /**
   * Static factory method that converts {@link org.springframework.data.domain.Page} instance
   * to a {@link Page} record class.
   * @param page {@link org.springframework.data.domain.Page} instance
   * @param <T> - generic type of the object that is in the collection
   * @return {@link Page} instance.
   */
  public static <T> Page<T> fromPage(org.springframework.data.domain.Page<T> page) {
    return new Page<>(
            Objects.requireNonNull(page.getContent(), "Content must not be null"),
            page.getNumber(),
            page.getTotalElements(),
            page.getTotalPages()
    );
  }
}
