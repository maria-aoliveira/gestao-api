package com.personal.gestao.services;

import com.personal.gestao.dtos.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDTO);

    List<TaskDto> listAllTasks();

    TaskDto updateTask(Long id, TaskDto taskDTO);

    void deleteTask(Long id);

    TaskDto findTaskById(Long id);

    TaskDto findByTaskTitle(String name);
}
