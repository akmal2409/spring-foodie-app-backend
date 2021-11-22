package com.akmal.springfoodieappbackend.dto.tomtom;

/**
 * The class represents the TomTom API error object that is returned to the client.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/11/2021 - 6:18 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record TomTomErrorResponse(String errorText,
                                  DetailedError detailedError,
                                  Integer httpStatusCode) {

  public record DetailedError(String code,
                              String message) {}
}
