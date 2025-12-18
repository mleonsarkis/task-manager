package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskDtos;
import com.example.taskmanager.entity.Project;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.repo.ProjectRepository;
import com.example.taskmanager.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

  private final ProjectRepository projectRepo;
  private final TaskRepository taskRepo;

  public TaskService(ProjectRepository projectRepo, TaskRepository taskRepo) {
    this.projectRepo = projectRepo;
    this.taskRepo = taskRepo;
  }

  @Transactional(readOnly = true)
  public List<TaskDtos.TaskResponse> list(UUID projectId, TaskStatus status) {
    ensureProject(projectId);

    List<Task> tasks = (status == null)
        ? taskRepo.findByProject_Id(projectId)
        : taskRepo.findByProject_IdAndStatus(projectId, status);

    return tasks.stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public TaskDtos.TaskResponse get(UUID projectId, UUID taskId) {
    ensureProject(projectId);
    Task task = taskRepo.findByIdAndProject_Id(taskId, projectId)
        .orElseThrow(() -> new NotFoundException("Task not found in this project"));
    return toResponse(task);
  }

  @Transactional
  public TaskDtos.TaskResponse create(UUID projectId, TaskDtos.TaskCreateRequest req) {
    Project project = projectRepo.findById(projectId)
        .orElseThrow(() -> new NotFoundException("Project not found"));

    Task t = new Task();
    t.setProject(project);
    t.setTitle(req.title().trim());
    t.setDescription(req.description());
    t.setStatus(req.status() == null ? TaskStatus.TODO : req.status());
    t.setDueDate(req.dueDate());

    taskRepo.save(t);
    return toResponse(t);
  }

  @Transactional
  public TaskDtos.TaskResponse update(UUID projectId, UUID taskId, TaskDtos.TaskUpdateRequest req) {
    ensureProject(projectId);
    Task t = taskRepo.findByIdAndProject_Id(taskId, projectId)
        .orElseThrow(() -> new NotFoundException("Task not found in this project"));

    t.setTitle(req.title().trim());
    t.setDescription(req.description());
    t.setStatus(req.status() == null ? TaskStatus.TODO : req.status());
    t.setDueDate(req.dueDate());

    taskRepo.save(t);
    return toResponse(t);
  }

  @Transactional
  public void delete(UUID projectId, UUID taskId) {
    ensureProject(projectId);
    Task t = taskRepo.findByIdAndProject_Id(taskId, projectId)
        .orElseThrow(() -> new NotFoundException("Task not found in this project"));
    taskRepo.delete(t);
  }

  private void ensureProject(UUID projectId) {
    if (!projectRepo.existsById(projectId)) {
      throw new NotFoundException("Project not found");
    }
  }

  private TaskDtos.TaskResponse toResponse(Task t) {
    return new TaskDtos.TaskResponse(
        t.getId(),
        t.getProject().getId(),
        t.getTitle(),
        t.getDescription(),
        t.getStatus(),
        t.getDueDate(),
        t.getCreatedAt(),
        t.getUpdatedAt()
    );
  }
}
