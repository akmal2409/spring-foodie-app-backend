package com.akmal.springfoodieappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class represents the exception that is thrown if the file related operations fail.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 7:45 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileException extends RuntimeException {

  public FileException() {
    super("The was an error while working with files");
  }

  public FileException(String message) {
    super(message);
  }

  public FileException(String message, Throwable cause) {
    super(message, cause);
  }

  public FileException(Throwable cause) {
    super(cause);
  }

  public FileException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
