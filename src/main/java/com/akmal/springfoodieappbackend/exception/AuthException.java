package com.akmal.springfoodieappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 *
 * <h1>AuthException</h1>
 *
 * Custom exception that will be thrown in case authentication checks fail.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 12/11/2021 - 7:11 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthException extends RuntimeException {

  public AuthException() {}

  public AuthException(String message) {
    super(message);
  }

  public AuthException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuthException(Throwable cause) {
    super(cause);
  }

  public AuthException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
