package com.example.taskmanager.dto;

import com.example.taskmanager.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class TaskDtos {

  public record TaskCreateRequest(
      @NotBlank @Size(min = 3, max = 200) String title,
      @Size(max = 2000) String description,
      TaskStatus status,
      LocalDate dueDate
  ) {}

  public record TaskUpdateRequest(
      @NotBlank @Size(min = 3, max = 200) String title,
      @Size(max = 2000) String description,
      TaskStatus status,
      LocalDate dueDate
  ) {}

  public record TaskResponse(
      UUID id,
      UUID projectId,
      String title,
      String description,
      TaskStatus status,
      LocalDate dueDate,
      Instant createdAt,
      Instant updatedAt
  ) {}
}
