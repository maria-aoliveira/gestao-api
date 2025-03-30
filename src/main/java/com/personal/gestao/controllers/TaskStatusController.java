package com.personal.gestao.controllers;

import com.personal.gestao.dtos.taskstatus.TaskStatusPageResponseDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusRequestDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusResponseDto;
import com.personal.gestao.services.TaskStatusService;
import com.personal.gestao.swagger.TaskStatusApiDocumentation;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class TaskStatusController implements TaskStatusApiDocumentation {

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
    public ResponseEntity<TaskStatusPageResponseDto> listTaskStatus(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "id") String sort,
                                                                    @RequestParam(defaultValue = "asc") String direction) {

        Sort sortOrder = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        TaskStatusPageResponseDto statusList = taskStatusService.listAllStatus(pageable);
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
