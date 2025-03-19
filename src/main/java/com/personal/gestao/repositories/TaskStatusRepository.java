package com.personal.gestao.repositories;

import com.personal.gestao.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    TaskStatus findByStatus(String status);
}