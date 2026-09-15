package com.fieldops.shared.error;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorResponse> handleApiException(
      ApiException ex, HttpServletRequest request) {
    return ResponseEntity.status(ex.getStatus())
        .body(buildError(ex.getStatus(), ex.getCode(), ex.getMessage(), request, null));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<ErrorResponse.FieldError> fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(fe -> new ErrorResponse.FieldError(fe.getField(), fe.getDefaultMessage()))
            .toList();
    return ResponseEntity.badRequest()
        .body(
            buildError(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "Validation failed",
                request,
                fieldErrors));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthentication(
      AuthenticationException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            buildError(
                HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Unauthorized", request, null));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(
      AccessDeniedException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            buildError(
                HttpStatus.FORBIDDEN, "ACCESS_DENIED", "Access denied", request, null));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleConflict(
      DataIntegrityViolationException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(
            buildError(
                HttpStatus.CONFLICT, "CONFLICT", "Conflict", request, null));
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ErrorResponse> handlePayloadTooLarge(
      MaxUploadSizeExceededException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
        .body(
            buildError(
                HttpStatus.PAYLOAD_TOO_LARGE,
                "PAYLOAD_TOO_LARGE",
                "Payload too large",
                request,
                null));
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleUnsupportedMediaType(
      HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        .body(
            buildError(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "UNSUPPORTED_MEDIA_TYPE",
                "Unsupported media type",
                request,
                null));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(
      HttpMessageNotReadableException ex, HttpServletRequest request) {
    return ResponseEntity.badRequest()
        .body(
            buildError(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "Malformed request body",
                request,
                null));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Internal server error",
                request,
                null));
  }

  private ErrorResponse buildError(
      HttpStatus status,
      String code,
      String message,
      HttpServletRequest request,
      List<ErrorResponse.FieldError> fieldErrors) {
    String requestId = MDC.get("requestId");
    return new ErrorResponse(
        Instant.now(),
        status.value(),
        code,
        message,
        request.getRequestURI(),
        requestId,
        fieldErrors);
  }
}
