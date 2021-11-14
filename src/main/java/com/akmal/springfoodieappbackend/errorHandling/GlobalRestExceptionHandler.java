package com.akmal.springfoodieappbackend.errorHandling;

import com.akmal.springfoodieappbackend.exception.AuthException;
import com.akmal.springfoodieappbackend.exception.InsufficientRightsException;
import com.akmal.springfoodieappbackend.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;
import java.util.Properties;

/**
 * <h1>GlobalRestExceptionHandler</h1>
 * The class acts as a global middleware that intercepts all API errors
 * and wraps them into standardized object {@link ApiError}.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 14/11/2021 - 1:50 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
  private final Properties errorMsgProperties;

  public GlobalRestExceptionHandler(@Qualifier("errorMessageProps") Properties properties) {
    this.errorMsgProperties = properties;
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return buildResponseEntity(buildApiError("FD00000", status, ex));
  }

  @ExceptionHandler(AuthException.class)
  protected ResponseEntity<Object> handleAuthException(AuthException ex) {
    return buildResponseEntity(buildApiError("FD00001", HttpStatus.UNAUTHORIZED, ex));
  }

  @ExceptionHandler(InsufficientRightsException.class)
  protected ResponseEntity<Object> handleInsufficientRightsException(AuthException ex) {
    return buildResponseEntity(buildApiError("FD00002", HttpStatus.FORBIDDEN, ex));
  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundException(AuthException ex) {
    return buildResponseEntity(buildApiError("FD00003", HttpStatus.NOT_FOUND, ex));
  }

  /**
   * The method is responsible for building the {@link ResponseEntity} object
   * with {@link ApiError} object as body and {@link HttpStatus} as status.
   * @param apiError {@link ApiError} instance
   * @return {@link ResponseEntity} instance with body and status
   */
  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.status());
  }

  /**
   * Method is responsible for creating an {@link ApiError} class's instance
   * with populated error message loaded from the properties files using an errorCode key.
   * @param errorCode code from the available list in {@code error-messages.yml}
   * @param httpStatus http status of operation
   * @param ex exception that was thrown
   * @return instance of {@link ApiError} class
   */
  private ApiError buildApiError(String errorCode, HttpStatus httpStatus, Throwable ex) {
    String errorMessage = this.errorMsgProperties.getProperty(String.format("%s.message", errorCode));

    return ApiError.builder()
            .message(Optional.ofNullable(errorMessage).orElse("Error occurred"))
            .status(httpStatus)
            .errorCode(errorCode)
            .debugMessage(ex.getLocalizedMessage())
            .build();
  }
}
