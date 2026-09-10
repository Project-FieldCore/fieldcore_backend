package com.fieldops.auth.controller;

import com.fieldops.shared.error.ApiException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @PostMapping("/login")
  public Map<String, String> login(@RequestBody Map<String, String> body) {
    throw notImplemented("login");
  }

  @PostMapping("/refresh")
  public Map<String, String> refresh(@RequestBody Map<String, String> body) {
    throw notImplemented("refresh");
  }

  private static ApiException notImplemented(String operation) {
    return new ApiException(
        HttpStatus.NOT_IMPLEMENTED,
        "NOT_IMPLEMENTED",
        "Auth " + operation + " not implemented (Sprint 2)");
  }
}
