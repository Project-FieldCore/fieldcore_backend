package com.fieldops.shared.error;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
    Instant timestamp,
    int status,
    String code,
    String message,
    String path,
    String requestId,
    List<FieldError> fieldErrors) {

  public record FieldError(String field, String message) {}
}
