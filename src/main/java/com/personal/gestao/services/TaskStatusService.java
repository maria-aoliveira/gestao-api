package com.personal.gestao.services;

import com.personal.gestao.dtos.TaskDto;
import com.personal.gestao.dtos.TaskStatusDto;
import com.personal.gestao.entities.TaskStatus;

import java.util.List;

public interface TaskStatusService {
    TaskStatusDto createStatus(TaskStatusDto taskStatusDto);

    List<TaskStatusDto> listAllStatus();

    TaskStatusDto updateStatus(Long id, TaskStatusDto taskStatusDto);

    void deleteStatus(Long id);

    TaskStatusDto findStatusById(Long id);
}
