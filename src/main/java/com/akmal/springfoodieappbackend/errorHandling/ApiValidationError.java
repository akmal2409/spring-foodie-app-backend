package com.akmal.springfoodieappbackend.errorHandling;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <h1>ApiValidationError</h1>
 * The class represents the error object that will be returned in
 * case if validation in a certain method call fails.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 14/11/2021 - 1:36 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = false)
@ToString
public class ApiValidationError extends ApiSubError {
  private final String object;
  private final String field;
  private final Object rejectedValue;
  private final String message;

  private ApiValidationError(String object, String field, Object rejectedValue, String message) {
    this.object = object;
    this.field = field;
    this.rejectedValue = rejectedValue;
    this.message = message;
  }

  /**
   * Method returns the {@link Builder}. It requires mandatory
   * presence of message argument and therefore, message cannot be changed
   * and must be set at the beginning.
   * @param message error message
   * @return instance of {@link Builder} class
   */
  public static Builder message(String message) {
    return new Builder()
            .message(message);
  }
  /**
   * Inner builder class following the Builder Design Pattern.
   * Allows user to define optional number of arguments.
   */
  public static class Builder {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public Builder object(String object) {
      this.object = object;
      return this;
    }

    public Builder field(String field) {
      this.field = field;
      return this;
    }

    public Builder rejectedValue(Object rejectedValue) {
      this.rejectedValue = rejectedValue;
      return this;
    }

    private Builder message(String message) {
      this.message = message;
      return this;
    }

    public ApiValidationError build() {
      return new ApiValidationError(this.object, this.field, this.rejectedValue, this.message);
    }
  }
}
