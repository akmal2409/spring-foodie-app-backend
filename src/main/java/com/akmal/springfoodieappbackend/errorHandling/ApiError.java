package com.akmal.springfoodieappbackend.errorHandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ApiError</h1>
 * The record class encapsulates common field of all the server side errors.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 14/11/2021 - 1:19 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */

public record ApiError(HttpStatus status,
                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                       LocalDateTime timestamp,
                       String message,
                       String errorCode,
                       String debugMessage,
                       List<ApiSubError> subErrors) {

  /**
   * Method returns an instance of the {@link Builder} class
   *
   * @return instance of {@link Builder} class
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Inner Builder class that follows the Builder Design Pattern.
   * Constructs the builder object and lets the user define optional
   * number of parameters.
   */
  public static class Builder {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String errorCode;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors = new ArrayList<>();

    public Builder status(HttpStatus status) {
      this.status = status;
      return this;
    }

    public Builder timestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder debugMessage(String debugMessage) {
      this.debugMessage = debugMessage;
      return this;
    }

    public Builder subErrors(List<ApiSubError> subErrors) {
      this.subErrors = subErrors;
      return this;
    }

    public Builder errorCode(String errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public ApiError build() {
      return new ApiError(this.status, this.timestamp, this.message, this.errorCode, this.debugMessage, this.subErrors);
    }
  }
}
