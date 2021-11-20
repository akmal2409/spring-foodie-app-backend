package com.akmal.springfoodieappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotImplementedException is thrown in case some of teh functionality is not yet available.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 8:16 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotImplementedException extends RuntimeException {

  public NotImplementedException() {
  }

  public NotImplementedException(String message) {
    super(message);
  }

  public NotImplementedException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotImplementedException(Throwable cause) {
    super(cause);
  }

  public NotImplementedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
