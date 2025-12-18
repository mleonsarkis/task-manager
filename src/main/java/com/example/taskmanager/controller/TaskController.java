package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskDtos;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public List<TaskDtos.TaskResponse> list(
      @PathVariable UUID projectId,
      @RequestParam(required = false) TaskStatus status
  ) {
    return taskService.list(projectId, status);
  }

  @GetMapping("/{taskId}")
  public TaskDtos.TaskResponse get(@PathVariable UUID projectId, @PathVariable UUID taskId) {
    return taskService.get(projectId, taskId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskDtos.TaskResponse create(
      @PathVariable UUID projectId,
      @Valid @RequestBody TaskDtos.TaskCreateRequest req
  ) {
    return taskService.create(projectId, req);
  }

  @PutMapping("/{taskId}")
  public TaskDtos.TaskResponse update(
      @PathVariable UUID projectId,
      @PathVariable UUID taskId,
      @Valid @RequestBody TaskDtos.TaskUpdateRequest req
  ) {
    return taskService.update(projectId, taskId, req);
  }

  @DeleteMapping("/{taskId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID projectId, @PathVariable UUID taskId) {
    taskService.delete(projectId, taskId);
  }
}
