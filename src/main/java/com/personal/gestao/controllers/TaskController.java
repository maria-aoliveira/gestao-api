package com.personal.gestao.controllers;

import com.personal.gestao.dtos.TaskDto;
import com.personal.gestao.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> criarTask(@RequestBody @Valid TaskDto taskDTO) {
        TaskDto createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.status(201).body(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listarTasks() {
        List<TaskDto> tasks = taskService.listAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> buscarTaskPorId(@PathVariable Long id) {
        TaskDto task = taskService.findTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> atualizarTask(@PathVariable Long id, @RequestBody @Valid TaskDto taskDTO) {
        TaskDto updatedTask = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
