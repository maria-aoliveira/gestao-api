package com.personal.gestao.dtos.task;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;

    private Long userId;
    private Long categoryId;
    private Long taskStatusId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
