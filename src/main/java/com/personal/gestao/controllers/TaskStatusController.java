package com.personal.gestao.controllers;

import com.personal.gestao.dtos.TaskStatusDto;
import com.personal.gestao.services.TaskStatusService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class TaskStatusController {

    private final TaskStatusService taskStatusService;

    public TaskStatusController(TaskStatusService taskStatusService){
        this.taskStatusService = taskStatusService;
    }

    @PostMapping
    public ResponseEntity<TaskStatusDto> createStatus(@RequestBody @Valid TaskStatusDto taskStatusDto){
        TaskStatusDto status = taskStatusService.createStatus(taskStatusDto);
        return ResponseEntity.status(201).body(status);
    }

    @GetMapping
    public ResponseEntity<List<TaskStatusDto>> listTaskStatus() {
        List<TaskStatusDto> statusList = taskStatusService.listAllStatus();
        return ResponseEntity.ok(statusList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskStatusDto> findStatusById(@PathVariable Long id) {
        TaskStatusDto taskStatus = taskStatusService.findStatusById(id);
        return ResponseEntity.ok(taskStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskStatusDto> updateTaskStatus(@PathVariable Long id,
                                                             @RequestBody @Valid TaskStatusDto taskStatusDto) {
        TaskStatusDto statusAtualizado = taskStatusService.updateStatus(id, taskStatusDto);
        return ResponseEntity.ok(statusAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskStatus(@PathVariable Long id) {
        taskStatusService.deleteStatus(id);
        return ResponseEntity.noContent().build();
    }
}
