package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.taskstatus.TaskStatusRequestDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusResponseDto;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.mappers.TaskStatusMapper;
import com.personal.gestao.repositories.TaskStatusRepository;
import com.personal.gestao.services.TaskStatusService;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.personal.gestao.utils.validation.ValidationUtils.*;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusServiceImpl (TaskStatusRepository taskStatusRepository){
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    @Transactional
    public TaskStatusResponseDto createStatus (TaskStatusRequestDto taskStatusRequestDto) {
        validateCreate(taskStatusRequestDto.getStatus());
        TaskStatus taskStatus = TaskStatusMapper.toEntity(taskStatusRequestDto);
        taskStatus = taskStatusRepository.save(taskStatus);
        return TaskStatusMapper.toTaskStatusDto(taskStatus);
    }

    @Override
    public List<TaskStatusResponseDto> listAllStatus() {
        List<TaskStatus> taskStatuses = taskStatusRepository.findAll();
        return taskStatuses.stream().map(TaskStatusMapper::toTaskStatusDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskStatusResponseDto updateStatus(Long id, TaskStatusRequestDto taskStatusRequestDto) {
        TaskStatus taskStatus = getStatusEntityById(id);
        validateUpdate(taskStatusRequestDto.getStatus(), id);
        taskStatus.setStatus(taskStatusRequestDto.getStatus());
        taskStatus = taskStatusRepository.save(taskStatus);
        return TaskStatusMapper.toTaskStatusDto(taskStatus);
    }

    @Override
    @Transactional
    public void deleteStatus(Long id) {
        this.findStatusById(id);
        taskStatusRepository.deleteById(id);
    }

    @Override
    public TaskStatusResponseDto findStatusById(Long id) {
        return TaskStatusMapper.toTaskStatusDto(getStatusEntityById(id));
    }

    @Override
    public TaskStatusResponseDto findByStatus(String status){
        TaskStatus taskStatus = taskStatusRepository.findByStatus(status).orElseThrow(() ->
                new ResourceNotFoundException("Status '" + status + "'not found"));
        return TaskStatusMapper.toTaskStatusDto(taskStatus);
    }

    public TaskStatus getStatusEntityById(Long id){
        return taskStatusRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Status not found"));
    }

    public void validateCreate(String status){
        validateRequiredField(status, "Task status");
        validateDuplicateOnCreate(status,"Task status",
                taskStatusRepository::findByStatus);
    }

    private void validateUpdate(String status, Long id){
        validateRequiredField(status, "Task status");
        validateDuplicateOnUpdate(status, id, "Task",
                taskStatusRepository::findByStatus, TaskStatus::getId);
    }
}