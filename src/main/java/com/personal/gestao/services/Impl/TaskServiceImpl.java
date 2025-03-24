package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.TaskDto;
import com.personal.gestao.entities.Category;
import com.personal.gestao.entities.Task;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.entities.User;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.repositories.CategoryRepository;
import com.personal.gestao.repositories.TaskRepository;
import com.personal.gestao.repositories.TaskStatusRepository;
import com.personal.gestao.repositories.UserRepository;
import com.personal.gestao.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TaskStatusRepository taskStatusRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository,
                           CategoryRepository categoryRepository, TaskStatusRepository taskStatusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public TaskDto createTask(TaskDto taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category category = findCategoryIfPresent(taskDTO.getCategoryId());
        TaskStatus taskStatus = findOrDefaultStatus(taskDTO.getTaskStatusId());
        Task task = taskDTO.toEntity(user, category, taskStatus);

        taskRepository.save(task);

        return TaskDto.toTaskDto(task);
    }

    @Override
    public List<TaskDto> listAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskDto::toTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDTO) {
        Task task = getTaskEntityById(id);

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());

        taskRepository.save(task);

        return TaskDto.toTaskDto(task);
    }

    @Override
    public void deleteTask(Long id) {
        getTaskEntityById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto findTaskById(Long id) {
        return TaskDto.toTaskDto(getTaskEntityById(id));
    }

    public Task getTaskEntityById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    private Category findCategoryIfPresent(Long categoryId) {
        if (categoryId == null) return null;
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private TaskStatus findOrDefaultStatus(Long statusId) {
        if (statusId != null) {
            return taskStatusRepository.findById(statusId)
                    .orElseThrow(() -> new ResourceNotFoundException("TaskStatus not found"));
        }
        return taskStatusRepository.findByStatus("New")
                .orElseThrow(() -> new ResourceNotFoundException("Default TaskStatus not found"));
    }

}
