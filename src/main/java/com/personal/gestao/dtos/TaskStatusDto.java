package com.personal.gestao.dtos;

import com.personal.gestao.entities.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusDto {
    private Long id;
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
