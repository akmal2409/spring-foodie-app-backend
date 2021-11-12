package com.akmal.springfoodieappbackend.exception;

/**
 * <h1>UnsupportedTypeException</h1>
 * Custom exception that will be thrown in case the server does not support certain action
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 12/11/2021 - 7:15 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public class UnsupportedTypeException extends RuntimeException {

  public UnsupportedTypeException() {
  }

  public UnsupportedTypeException(String message) {
    super(message);
  }

  public UnsupportedTypeException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnsupportedTypeException(Throwable cause) {
    super(cause);
  }

  public UnsupportedTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
