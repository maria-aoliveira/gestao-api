package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.task.TaskPageResponseDto;
import com.personal.gestao.dtos.task.TaskRequestDto;
import com.personal.gestao.dtos.task.TaskResponseDto;
import com.personal.gestao.entities.Category;
import com.personal.gestao.entities.Task;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.entities.User;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.mappers.TaskMapper;
import com.personal.gestao.repositories.CategoryRepository;
import com.personal.gestao.repositories.TaskRepository;
import com.personal.gestao.repositories.TaskStatusRepository;
import com.personal.gestao.repositories.UserRepository;
import com.personal.gestao.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.personal.gestao.utils.validation.ValidationUtils.*;

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
    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskRequestDTO) {
        validateCreate(taskRequestDTO.getTitle(), taskRequestDTO.getDueDate());

        User user = findUserById(taskRequestDTO.getUserId());
        Category category = findCategoryIfPresent(taskRequestDTO.getCategoryId());
        TaskStatus taskStatus = findOrDefaultStatus(taskRequestDTO.getTaskStatusId());

        Task task = TaskMapper.toTaskEntity(taskRequestDTO, user, category, taskStatus);

        task = taskRepository.save(task);

        return TaskMapper.toTaskDto(task);
    }

    @Override
    public TaskPageResponseDto listAllTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return TaskPageResponseDto.fromPage(tasks);
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDTO) {

        validateUpdate(taskRequestDTO.getTitle(), id, taskRequestDTO.getDueDate());

        Task task = getTaskEntityById(id);

        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDueDate(taskRequestDTO.getDueDate());

        taskRepository.save(task);

        return TaskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        getTaskEntityById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDto findTaskById(Long id) {
        return TaskMapper.toTaskDto(getTaskEntityById(id));
    }

    @Override
    public TaskResponseDto findByTaskTitle(String title){
        Task task = taskRepository.findByTitle(title)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        return TaskMapper.toTaskDto(task);
    }

    public Task getTaskEntityById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    private User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
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

    private void validateDueDate(LocalDateTime dueDate) {
        if (dueDate != null && dueDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Due date must be in the future");
        }
    }

    private void validateCreate(String name, LocalDateTime date){
        validateDueDate(date);
        validateRequiredField(name, "Task title");
        validateDuplicateOnCreate(name,"Task",
                taskRepository::findByTitle);
    }

    private void validateUpdate(String name, Long id, LocalDateTime date){
        validateDueDate(date);
        validateRequiredField(name, "Task title");
        validateDuplicateOnUpdate(name, id, "Task",
                taskRepository::findByTitle, Task::getId);
    }
}
