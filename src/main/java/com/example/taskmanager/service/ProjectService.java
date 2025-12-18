package com.example.taskmanager.service;

import com.example.taskmanager.dto.ProjectDtos;
import com.example.taskmanager.entity.Project;
import com.example.taskmanager.exception.ConflictException;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.repo.ProjectRepository;
import com.example.taskmanager.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

  private final ProjectRepository projectRepo;
  private final TaskRepository taskRepo;

  public ProjectService(ProjectRepository projectRepo, TaskRepository taskRepo) {
    this.projectRepo = projectRepo;
    this.taskRepo = taskRepo;
  }

  @Transactional(readOnly = true)
  public List<ProjectDtos.ProjectResponse> list() {
    return projectRepo.findAll().stream()
        .map(p -> new ProjectDtos.ProjectResponse(
            p.getId(),
            p.getName(),
            p.getDescription(),
            p.getCreatedAt(),
            p.getUpdatedAt(),
            taskRepo.findByProject_Id(p.getId()).size()
        ))
        .toList();
  }

  @Transactional(readOnly = true)
  public ProjectDtos.ProjectResponse get(UUID projectId) {
    Project p = projectRepo.findById(projectId)
        .orElseThrow(() -> new NotFoundException("Project not found"));
    long taskCount = taskRepo.findByProject_Id(projectId).size();
    return new ProjectDtos.ProjectResponse(
        p.getId(), p.getName(), p.getDescription(), p.getCreatedAt(), p.getUpdatedAt(), taskCount
    );
  }

  @Transactional
  public ProjectDtos.ProjectResponse create(ProjectDtos.ProjectCreateRequest req) {
    if (projectRepo.existsByNameIgnoreCase(req.name())) {
      throw new ConflictException("Project name already exists");
    }
    Project p = new Project();
    p.setName(req.name().trim());
    p.setDescription(req.description());
    projectRepo.save(p);
    return new ProjectDtos.ProjectResponse(
        p.getId(), p.getName(), p.getDescription(), p.getCreatedAt(), p.getUpdatedAt(), 0
    );
  }

  @Transactional
  public ProjectDtos.ProjectResponse update(UUID projectId, ProjectDtos.ProjectUpdateRequest req) {
    Project p = projectRepo.findById(projectId)
        .orElseThrow(() -> new NotFoundException("Project not found"));

    String newName = req.name().trim();
    projectRepo.findByNameIgnoreCase(newName).ifPresent(existing -> {
      if (!existing.getId().equals(projectId)) {
        throw new ConflictException("Project name already exists");
      }
    });

    p.setName(newName);
    p.setDescription(req.description());
    projectRepo.save(p);

    long taskCount = taskRepo.findByProject_Id(projectId).size();
    return new ProjectDtos.ProjectResponse(
        p.getId(), p.getName(), p.getDescription(), p.getCreatedAt(), p.getUpdatedAt(), taskCount
    );
  }

  @Transactional
  public void delete(UUID projectId) {
    if (!projectRepo.existsById(projectId)) {
      throw new NotFoundException("Project not found");
    }
    projectRepo.deleteById(projectId); // cascades tasks via mapping
  }
}
