package com.akmal.springfoodieappbackend.errorHandling;

import com.akmal.springfoodieappbackend.exception.*;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;
import java.util.Properties;

/**
 *
 *
 * <h1>GlobalRestExceptionHandler</h1>
 *
 * The class acts as a global middleware that intercepts all API errors and wraps them into
 * standardized object {@link ApiError}.
 *
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

  @ExceptionHandler(AuthException.class)
  protected ResponseEntity<Object> handleAuthException(AuthException ex) {
    return buildResponseEntity(buildApiError("FD00001", HttpStatus.UNAUTHORIZED, ex));
  }

  @ExceptionHandler(InsufficientRightsException.class)
  protected ResponseEntity<Object> handleInsufficientRightsException(
      InsufficientRightsException ex) {
    return buildResponseEntity(buildApiError("FD00002", HttpStatus.FORBIDDEN, ex));
  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
    return buildResponseEntity(buildApiError("FD00003", HttpStatus.NOT_FOUND, ex));
  }

  @ExceptionHandler(FileException.class)
  protected ResponseEntity<Object> handleFileException(FileException ex) {
    return buildResponseEntity(buildApiError("FD00019", HttpStatus.INTERNAL_SERVER_ERROR, ex));
  }

  @ExceptionHandler(InvalidFileException.class)
  protected ResponseEntity<Object> handleInvalidFileException(InvalidFileException ex) {
    return buildResponseEntity(buildApiError("FD00020", HttpStatus.BAD_REQUEST, ex));
  }

  @ExceptionHandler(NotImplementedException.class)
  protected ResponseEntity<Object> handleNotImplementedException(NotImplementedException ex) {
    return buildResponseEntity(buildApiError("FD00021", HttpStatus.BAD_REQUEST, ex));
  }

  @ExceptionHandler(ExternalCallException.class)
  protected ResponseEntity<Object> handleExternalCallException(ExternalCallException ex) {
    return buildResponseEntity(buildApiError("FD00022", HttpStatus.BAD_REQUEST, ex));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00000", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00004", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00005", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      HttpMediaTypeNotAcceptableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00006", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(
      MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(buildApiError("FD00007", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00008", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00009", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleConversionNotSupported(
      ConversionNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00010", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(buildApiError("FD00011", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(
      HttpMessageNotWritableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00012", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00013", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(buildApiError("FD00014", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(buildApiError("FD00015", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(buildApiError("FD00016", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest webRequest) {
    return buildResponseEntity(buildApiError("FD00017", status, ex));
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(buildApiError("FD00018", status, ex));
  }

  /**
   * The method is responsible for building the {@link ResponseEntity} object with {@link ApiError}
   * object as body and {@link HttpStatus} as status.
   *
   * @param apiError {@link ApiError} instance
   * @return {@link ResponseEntity} instance with body and status
   */
  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.status());
  }

  /**
   * Method is responsible for creating an {@link ApiError} class's instance with populated error
   * message loaded from the properties files using an errorCode key.
   *
   * @param errorCode code from the available list in {@code error-messages.yml}
   * @param httpStatus http status of operation
   * @param ex exception that was thrown
   * @return instance of {@link ApiError} class
   */
  private ApiError buildApiError(String errorCode, HttpStatus httpStatus, Throwable ex) {
    String errorMessage =
        this.errorMsgProperties.getProperty(String.format("%s.message", errorCode));

    return ApiError.builder()
        .message(Optional.ofNullable(errorMessage).orElse("Error occurred"))
        .status(httpStatus)
        .errorCode(errorCode)
        .debugMessage(ex.getLocalizedMessage())
        .build();
  }
}
