package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.TaskDto;
import com.personal.gestao.entities.Category;
import com.personal.gestao.entities.Task;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.entities.User;
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
    public TaskDto criarTask(TaskDto taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(taskDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        TaskStatus taskStatus = taskStatusRepository.findById(taskDTO.getTaskStatusId())
                .orElseThrow(() -> new RuntimeException("TaskStatus not found"));

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setUser(user);
        task.setCategory(category);
        task.setTaskStatus(taskStatus);
        task.setDueDate(taskDTO.getDueDate());

        taskRepository.save(task);

        return TaskDto.fromEntity(task);
    }

    @Override
    public List<TaskDto> listarTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto atualizarTask(Long id, TaskDto taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());

        taskRepository.save(task);

        return TaskDto.fromEntity(task);
    }

    @Override
    public void excluirTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto buscarTaskPorId(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return TaskDto.fromEntity(task);
    }
}
