package com.personal.gestao.controllers;

import com.personal.gestao.dtos.TaskDto;
import com.personal.gestao.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto criarTask(@RequestBody @Valid TaskDto taskDTO) {
        return taskService.criarTask(taskDTO);
    }

    @GetMapping
    public List<TaskDto> listarTasks() {
        return taskService.listarTasks();
    }

    @GetMapping("/{id}")
    public TaskDto buscarTaskPorId(@PathVariable Long id) {
        return taskService.buscarTaskPorId(id);
    }

    @PutMapping("/{id}")
    public TaskDto atualizarTask(@PathVariable Long id, @RequestBody @Valid TaskDto taskDTO) {
        return taskService.atualizarTask(id, taskDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirTask(@PathVariable Long id) {
        taskService.excluirTask(id);
    }
}
