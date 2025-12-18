package com.example.taskmanager.repo;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
  List<Task> findByProject_Id(UUID projectId);
  List<Task> findByProject_IdAndStatus(UUID projectId, TaskStatus status);
  Optional<Task> findByIdAndProject_Id(UUID taskId, UUID projectId);
}
