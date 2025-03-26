package com.personal.gestao.mappers;

import com.personal.gestao.dtos.task.TaskRequestDto;
import com.personal.gestao.dtos.task.TaskResponseDto;
import com.personal.gestao.entities.Category;
import com.personal.gestao.entities.Task;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.entities.User;

public class TaskMapper {

    public static TaskResponseDto toTaskDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getUser() != null ? task.getUser().getId() : null,
                task.getCategory() != null ? task.getCategory().getId() : null,
                task.getTaskStatus() != null ? task.getTaskStatus().getId() : null,
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    public static Task toTaskEntity(TaskRequestDto taskRequestDto, User user, Category category, TaskStatus taskStatus) {
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setDueDate(taskRequestDto.getDueDate());
        task.setUser(user);
        task.setCategory(category);
        task.setTaskStatus(taskStatus);
        return task;
    }
}
