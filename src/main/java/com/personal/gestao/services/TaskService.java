package com.personal.gestao.services;

import com.personal.gestao.dtos.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto criarTask(TaskDto taskDTO);

    List<TaskDto> listarTasks();

    TaskDto atualizarTask(Long id, TaskDto taskDTO);

    void excluirTask(Long id);

    TaskDto buscarTaskPorId(Long id);

}
