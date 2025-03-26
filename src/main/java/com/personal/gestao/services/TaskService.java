package com.personal.gestao.services;

import com.personal.gestao.dtos.task.TaskPageResponseDto;
import com.personal.gestao.dtos.task.TaskRequestDto;
import com.personal.gestao.dtos.task.TaskResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    TaskResponseDto createTask(TaskRequestDto taskRequestDTO);

    TaskPageResponseDto listAllTasks(Pageable pageable);

    TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDTO);

    void deleteTask(Long id);

    TaskResponseDto findTaskById(Long id);

    TaskResponseDto findByTaskTitle(String name);
}
