package com.akmal.springfoodieappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InvalidFileException is thrown in case the file does not meet
 * server requirements and fails at validation stage.
 * Response {@link HttpStatus#BAD_REQUEST}
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 7:48 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileException extends RuntimeException {

  public InvalidFileException() {
  }

  public InvalidFileException(String message) {
    super(message);
  }

  public InvalidFileException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidFileException(Throwable cause) {
    super(cause);
  }

  public InvalidFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
