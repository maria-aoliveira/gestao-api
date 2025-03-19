package com.personal.gestao.dtos;

import com.personal.gestao.entities.Task;
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

    public static TaskDto fromEntity(Task task) {
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
}
