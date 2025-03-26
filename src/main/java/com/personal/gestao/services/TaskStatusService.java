package com.personal.gestao.services;

import com.personal.gestao.dtos.taskstatus.TaskStatusPageResponseDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusRequestDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskStatusService {
    TaskStatusResponseDto createStatus(TaskStatusRequestDto taskStatusRequestDto);

    TaskStatusPageResponseDto listAllStatus(Pageable pageable);

    TaskStatusResponseDto updateStatus(Long id, TaskStatusRequestDto taskStatusRequestDto);

    void deleteStatus(Long id);

    TaskStatusResponseDto findStatusById(Long id);

    TaskStatusResponseDto findByStatus(String status);
}
