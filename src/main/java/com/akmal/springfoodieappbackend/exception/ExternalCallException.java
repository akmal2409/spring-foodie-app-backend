package com.akmal.springfoodieappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class represents the exception that is thrown in case external API call fails/produces
 * exception.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/11/2021 - 6:11 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExternalCallException extends RuntimeException {
  public ExternalCallException() {}

  public ExternalCallException(String message) {
    super(message);
  }

  public ExternalCallException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExternalCallException(Throwable cause) {
    super(cause);
  }

  public ExternalCallException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
