package com.personal.gestao.controllers;

import com.personal.gestao.dtos.taskstatus.TaskStatusRequestDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusResponseDto;
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
    public ResponseEntity<TaskStatusResponseDto> createStatus(@RequestBody @Valid TaskStatusRequestDto taskStatusRequestDto){
        TaskStatusResponseDto status = taskStatusService.createStatus(taskStatusRequestDto);
        return ResponseEntity.status(201).body(status);
    }

    @GetMapping
    public ResponseEntity<List<TaskStatusResponseDto>> listTaskStatus() {
        List<TaskStatusResponseDto> statusList = taskStatusService.listAllStatus();
        return ResponseEntity.ok(statusList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskStatusResponseDto> findStatusById(@PathVariable Long id) {
        TaskStatusResponseDto taskStatus = taskStatusService.findStatusById(id);
        return ResponseEntity.ok(taskStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskStatusResponseDto> updateTaskStatus(@PathVariable Long id,
                                                                 @RequestBody @Valid TaskStatusRequestDto taskStatusRequestDto) {
        TaskStatusResponseDto statusAtualizado = taskStatusService.updateStatus(id, taskStatusRequestDto);
        return ResponseEntity.ok(statusAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskStatus(@PathVariable Long id) {
        taskStatusService.deleteStatus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-status")
    public ResponseEntity<TaskStatusResponseDto> getByStatus(@RequestParam String status) {
        return ResponseEntity.ok(taskStatusService.findByStatus(status));
    }

}
