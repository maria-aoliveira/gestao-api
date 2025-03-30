package com.personal.gestao.services.taskstatus;

import com.personal.gestao.dtos.taskstatus.TaskStatusPageResponseDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusRequestDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusResponseDto;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.mappers.TaskStatusMapper;
import com.personal.gestao.repositories.TaskStatusRepository;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        TaskStatus taskStatus = TaskStatusMapper.toTaskStatusEntity(taskStatusRequestDto);
        taskStatus = taskStatusRepository.save(taskStatus);
        return TaskStatusMapper.toTaskStatusDto(taskStatus);
    }

    @Override
    public TaskStatusPageResponseDto listAllStatus(Pageable pageable) {
        Page<TaskStatus> taskStatus = taskStatusRepository.findAll(pageable);
        return TaskStatusPageResponseDto.fromPage(taskStatus) ;
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