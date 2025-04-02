package com.personal.gestao.dtos.task;

import com.personal.gestao.entities.Category;
import com.personal.gestao.entities.Task;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    @NotBlank(message = "Title is mandatory")
    @Size(max = 255, message = "Title cannot have more than 255 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot have more than 255 characters")
    private String description;

    private Long categoryId;

    private Long taskStatusId;

    private LocalDateTime dueDate;
}
