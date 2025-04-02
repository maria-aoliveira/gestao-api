package com.personal.gestao.services.task;

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
import com.personal.gestao.services.context.CurrentUserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.personal.gestao.utils.validation.ValidationUtils.*;

@Service
public class TaskServiceImpl implements TaskService {

    private final CurrentUserService currentUserService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TaskStatusRepository taskStatusRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository,
                           CategoryRepository categoryRepository, TaskStatusRepository taskStatusRepository,
                           CurrentUserService currentUserService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskRequestDTO) {
        Long userId = currentUserService.getCurrentUserId();
        validateCreate(taskRequestDTO.getTitle(), taskRequestDTO.getDueDate(), userId);

        User user = userRepository.getReferenceById(userId);
        Category category = findOrDefaultCategory(taskRequestDTO.getCategoryId());
        TaskStatus taskStatus = findOrDefaultStatus(taskRequestDTO.getTaskStatusId());

        Task task = TaskMapper.toTaskEntity(taskRequestDTO, user, category, taskStatus);

        task = taskRepository.save(task);

        return TaskMapper.toTaskDto(task);
    }

    @Override
    public TaskPageResponseDto listAllTasks(Pageable pageable) {
        Long userId = currentUserService.getCurrentUserId();
        Page<Task> tasks = taskRepository.findAllActiveByUserId(userId, pageable);
        return TaskPageResponseDto.fromPage(tasks);
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDTO) {
        Long userId = currentUserService.getCurrentUserId();
        validateUpdate(taskRequestDTO.getTitle(), id, taskRequestDTO.getDueDate(), userId);

        Task task = getTaskEntityById(id, userId);

        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDueDate(taskRequestDTO.getDueDate());

        taskRepository.save(task);

        return TaskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Long userId = currentUserService.getCurrentUserId();
        Task task = getTaskEntityById(id, userId);
        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    @Override
    public TaskResponseDto findTaskById(Long id) {
        Long userId = currentUserService.getCurrentUserId();
        return TaskMapper.toTaskDto(getTaskEntityById(id, userId));
    }

    @Override
    public TaskResponseDto findByTaskTitle(String title){
        Long userId = currentUserService.getCurrentUserId();

        Task task = taskRepository.findActiveByTitleAndUserId(title, userId)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        return TaskMapper.toTaskDto(task);
    }

    public Task getTaskEntityById(Long id, Long userId) {
        Task task = taskRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You can't access this task");
        }

        return task;
    }

    //Método reservado para uso futuro por admin ou casos específicos
    private User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Category findOrDefaultCategory(Long categoryId) {
        if (categoryId != null) {
            return categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        }
        return categoryRepository.findByName("Geral")
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private TaskStatus findOrDefaultStatus(Long statusId) {
        if (statusId != null) {
            return taskStatusRepository.findById(statusId)
                    .orElseThrow(() -> new ResourceNotFoundException("TaskStatus not found"));
        }
        return taskStatusRepository.findByStatus("Novo")
                .orElseThrow(() -> new ResourceNotFoundException("Default TaskStatus not found"));
    }

    private void validateDueDate(LocalDateTime dueDate) {
        if (dueDate != null && dueDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Due date must be in the future");
        }
    }

    private void validateCreate(String name, LocalDateTime date, Long userId){
        validateDueDate(date);
        validateRequiredField(name, "Task title");
        validateDuplicateOnCreate(name,"Task",
                title -> taskRepository.findActiveByTitleAndUserId(title, userId));
    }

    private void validateUpdate(String name, Long id, LocalDateTime date, Long userId){
        validateDueDate(date);
        validateRequiredField(name, "Task title");
        validateDuplicateOnUpdate(name, id, "Task",
                title -> taskRepository.findActiveByTitleAndUserId(title, userId),
                Task::getId);
    }
}
