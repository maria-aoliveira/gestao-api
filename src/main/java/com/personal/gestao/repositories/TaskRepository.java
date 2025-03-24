package com.personal.gestao.repositories;

import com.personal.gestao.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);

    List<Task> findByUserId(Long userId);

    List<Task> findByCategoryId(Long categoryId);
}