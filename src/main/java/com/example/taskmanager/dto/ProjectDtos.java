package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public class ProjectDtos {

  public record ProjectCreateRequest(
      @NotBlank @Size(min = 3, max = 120) String name,
      @Size(max = 500) String description
  ) {}

  public record ProjectUpdateRequest(
      @NotBlank @Size(min = 3, max = 120) String name,
      @Size(max = 500) String description
  ) {}

  public record ProjectResponse(
      UUID id,
      String name,
      String description,
      Instant createdAt,
      Instant updatedAt,
      long taskCount
  ) {}
}
