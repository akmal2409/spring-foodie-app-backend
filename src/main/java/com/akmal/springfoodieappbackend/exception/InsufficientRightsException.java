package com.akmal.springfoodieappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception is thrown in case the user does not have sufficient rights to perform and
 * action.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 12/11/2021 - 9:23 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class InsufficientRightsException extends RuntimeException {

  public InsufficientRightsException() {}

  public InsufficientRightsException(String message) {
    super(message);
  }

  public InsufficientRightsException(String message, Throwable cause) {
    super(message, cause);
  }

  public InsufficientRightsException(Throwable cause) {
    super(cause);
  }

  public InsufficientRightsException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
