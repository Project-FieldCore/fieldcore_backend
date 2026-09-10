package com.fieldops.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Placeholder to validate security wiring in Sprint 1. */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public String listUsers() {
    return "[]";
  }
}
