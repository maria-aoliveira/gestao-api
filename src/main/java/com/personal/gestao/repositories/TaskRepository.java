package com.personal.gestao.repositories;

import com.personal.gestao.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    List<Task> findByCategoryId(Long categoryId);
}