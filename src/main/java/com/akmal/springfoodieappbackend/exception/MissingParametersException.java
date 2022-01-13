package com.akmal.springfoodieappbackend.exception;

/**
 * The class represents the custom exception thrown when the client has not provided required
 * request parameters.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 12/01/2022 - 20:41
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public class MissingParametersException extends RuntimeException {
  public MissingParametersException() {}

  public MissingParametersException(String message) {
    super(message);
  }

  public MissingParametersException(String message, Throwable cause) {
    super(message, cause);
  }

  public MissingParametersException(Throwable cause) {
    super(cause);
  }

  public MissingParametersException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
