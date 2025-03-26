package com.personal.gestao.mappers;

import com.personal.gestao.dtos.taskstatus.TaskStatusRequestDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusResponseDto;
import com.personal.gestao.entities.TaskStatus;

public class TaskStatusMapper {

    public static TaskStatusResponseDto toTaskStatusDto (TaskStatus taskStatus){
        return new TaskStatusResponseDto(
                taskStatus.getId(),
                taskStatus.getStatus()
        );
    }

    public static TaskStatus toTaskStatusEntity(TaskStatusRequestDto taskStatusRequestDto){
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setStatus(taskStatusRequestDto.getStatus());
        return taskStatus;
    }
}
