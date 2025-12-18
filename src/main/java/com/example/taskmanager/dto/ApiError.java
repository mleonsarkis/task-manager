package com.example.taskmanager.dto;

import java.time.Instant;
import java.util.List;

public class ApiError {
  public final Instant timestamp = Instant.now();
  public final String code;
  public final String message;
  public final List<FieldError> fieldErrors;

  public ApiError(String code, String message, List<FieldError> fieldErrors) {
    this.code = code;
    this.message = message;
    this.fieldErrors = fieldErrors;
  }

  public record FieldError(String field, String error) {}
}
