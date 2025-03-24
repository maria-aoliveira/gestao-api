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
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskDto taskDTO) {
        TaskDto task = taskService.createTask(taskDTO);
        return ResponseEntity.status(201).body(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listTasks() {
        List<TaskDto> tasks = taskService.listAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findByTaskId(@PathVariable Long id) {
        TaskDto task = taskService.findTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/by-title")
    public ResponseEntity<TaskDto> findByTaskTitle(@RequestParam String title){
        TaskDto task = taskService.findByTaskTitle(title);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDto taskDTO) {
        TaskDto task = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
