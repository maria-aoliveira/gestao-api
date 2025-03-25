package com.personal.gestao.controllers;

import com.personal.gestao.dtos.task.TaskRequestDto;
import com.personal.gestao.dtos.task.TaskResponseDto;
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
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto taskRequestDTO) {
        TaskResponseDto task = taskService.createTask(taskRequestDTO);
        return ResponseEntity.status(201).body(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> listTasks() {
        List<TaskResponseDto> tasks = taskService.listAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findByTaskId(@PathVariable Long id) {
        TaskResponseDto task = taskService.findTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/by-title")
    public ResponseEntity<TaskResponseDto> findByTaskTitle(@RequestParam String title){
        TaskResponseDto task = taskService.findByTaskTitle(title);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequestDto taskRequestDTO) {
        TaskResponseDto task = taskService.updateTask(id, taskRequestDTO);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
