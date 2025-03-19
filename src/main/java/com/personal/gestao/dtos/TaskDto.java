package com.personal.gestao.dtos;

import com.personal.gestao.entities.Category;
import com.personal.gestao.entities.Task;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.entities.User;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private Long categoryId;
    private Long taskStatusId;
    private Timestamp dueDate;

    public static TaskDto toTaskDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getUser().getId(),
                task.getCategory().getId(),
                task.getTaskStatus().getId(),
                task.getDueDate()
        );
    }

    public Task toEntity(User user, Category category, TaskStatus taskStatus) {
        Task task = new Task();
        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setDueDate(this.dueDate);
        task.setUser(user);
        task.setCategory(category);
        task.setTaskStatus(taskStatus);
        return task;
    }
}
