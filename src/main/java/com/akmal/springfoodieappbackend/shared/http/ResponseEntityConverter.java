package com.akmal.springfoodieappbackend.shared.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

/**
 * The class is responsible for converting the given objects into the {@link org.springframework.http.ResponseEntity}
 * class and setting appropriate HTTP status codes. Eliminates boilerplate code and acts as a helper class.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 24/10/2021 - 5:19 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public final class ResponseEntityConverter {

  /**
   * Method is responsible for converting the object into the {@link ResponseEntity} class
   * with the status {@link HttpStatus#OK}.
   *
   * @param object - any object
   * @param <E>    - generic type
   * @return responseEntity - {@link ResponseEntity} object
   */
  public static <E> ResponseEntity<E> ok(E object) {
    return withStatus(object, HttpStatus.OK);
  }

  /**
   * Method is responsible for converting the object into the {@link ResponseEntity} class
   * with custom status {@link HttpStatus}.
   *
   * If the value is null, or it is an instance of {@link Collection} and it is empty,
   * then it will return an empty response entity with the HTTP Status {@link HttpStatus#NO_CONTENT}.
   * Else it will return the ResponseEntity with the provided status.
   * @param object - any object
   * @param status - {@link HttpStatus} Enum
   * @param <E> - generic type
   * @return responseEntity - {@link ResponseEntity} object
   */
  public static <E> ResponseEntity<E> withStatus(E object, HttpStatus status) {
    if (object == null || (object instanceof Collection<?> collection && collection.isEmpty())) {
      return (ResponseEntity<E>) ResponseEntity.noContent();
    }

    return ResponseEntity.status(status)
            .body(object);
  }
}
