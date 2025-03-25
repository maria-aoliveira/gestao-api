package com.personal.gestao.services;

import com.personal.gestao.dtos.taskstatus.TaskStatusRequestDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusResponseDto;

import java.util.List;

public interface TaskStatusService {
    TaskStatusResponseDto createStatus(TaskStatusRequestDto taskStatusRequestDto);

    List<TaskStatusResponseDto> listAllStatus();

    TaskStatusResponseDto updateStatus(Long id, TaskStatusRequestDto taskStatusRequestDto);

    void deleteStatus(Long id);

    TaskStatusResponseDto findStatusById(Long id);

    TaskStatusResponseDto findByStatus(String status);
}
