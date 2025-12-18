package com.example.taskmanager.controller;

import com.example.taskmanager.dto.ProjectDtos;
import com.example.taskmanager.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping
  public List<ProjectDtos.ProjectResponse> list() {
    return projectService.list();
  }

  @GetMapping("/{projectId}")
  public ProjectDtos.ProjectResponse get(@PathVariable UUID projectId) {
    return projectService.get(projectId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProjectDtos.ProjectResponse create(@Valid @RequestBody ProjectDtos.ProjectCreateRequest req) {
    return projectService.create(req);
  }

  @PutMapping("/{projectId}")
  public ProjectDtos.ProjectResponse update(
      @PathVariable UUID projectId,
      @Valid @RequestBody ProjectDtos.ProjectUpdateRequest req
  ) {
    return projectService.update(projectId, req);
  }

  @DeleteMapping("/{projectId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID projectId) {
    projectService.delete(projectId);
  }
}
