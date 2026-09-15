package com.fieldops.shared.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fieldops.shared.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final ObjectMapper OBJECT_MAPPER =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    writeError(response, request, 401, "UNAUTHORIZED", "Unauthorized");
  }

  private static void writeError(
      HttpServletResponse response,
      HttpServletRequest request,
      int status,
      String code,
      String message)
      throws IOException {
    ErrorResponse body =
        new ErrorResponse(
            Instant.now(),
            status,
            code,
            message,
            request.getRequestURI(),
            MDC.get("requestId"),
            null);
    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    OBJECT_MAPPER.writeValue(response.getOutputStream(), body);
  }
}
