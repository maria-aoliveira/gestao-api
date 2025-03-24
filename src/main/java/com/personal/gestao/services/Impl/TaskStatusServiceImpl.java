package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.TaskStatusDto;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.repositories.TaskStatusRepository;
import com.personal.gestao.services.TaskStatusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusServiceImpl (TaskStatusRepository taskStatusRepository){
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public TaskStatusDto createStatus (TaskStatusDto taskStatusDto) {
        TaskStatus taskStatus = taskStatusDto.toEntity();
        taskStatus = taskStatusRepository.save(taskStatus);
        return TaskStatusDto.toTaskStatusDto(taskStatus);
    }

    @Override
    public List<TaskStatusDto> listAllStatus() {
        List<TaskStatus> taskStatuses = taskStatusRepository.findAll();
        return taskStatuses.stream().map(TaskStatusDto::toTaskStatusDto).collect(Collectors.toList());
    }

    @Override
    public TaskStatusDto updateStatus(Long id, TaskStatusDto taskStatusDto) {
        TaskStatus taskStatus = taskStatusRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Status not found"));
        taskStatus.setStatus(taskStatusDto.getStatus());
        taskStatus = taskStatusRepository.save(taskStatus);
        return TaskStatusDto.toTaskStatusDto(taskStatus);
    }

    @Override
    public void deleteStatus(Long id) {
        this.findStatusById(id);
        taskStatusRepository.deleteById(id);
    }

    @Override
    public TaskStatusDto findStatusById(Long id) {
        return TaskStatusDto.toTaskStatusDto(getStatusEntityById(id));
    }

    @Override
    public TaskStatusDto findByStatus(String status){
        TaskStatus taskStatus = taskStatusRepository.findByStatus(status).orElseThrow(() ->
                new ResourceNotFoundException("Status '" + status + "'not found"));;
        return TaskStatusDto.toTaskStatusDto(taskStatus);
    }


    public TaskStatus getStatusEntityById(Long id){
        return taskStatusRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Status not found"));
    }
}
