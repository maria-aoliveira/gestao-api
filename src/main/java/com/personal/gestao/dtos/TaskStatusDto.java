package com.personal.gestao.dtos;

import com.personal.gestao.entities.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusDto {
    private Long id;

    @NotBlank(message = "Status is mandatory")
    @Size(max = 50, message = "Status cannot have more than 50 characters")
    private String status;

    public static TaskStatusDto toTaskStatusDto (TaskStatus taskStatus){
        return new TaskStatusDto(
                taskStatus.getId(),
                taskStatus.getStatus()
        );
    }

    public TaskStatus toEntity(){
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setStatus(this.getStatus());
        return taskStatus;
    }
}
