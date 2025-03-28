package com.personal.gestao.repositories;

import com.personal.gestao.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Para validações e leitura - ignoram usuários desativados
    @Query("SELECT t FROM Task t WHERE t.id = :id AND t.deletedAt IS NULL")
    Optional<Task> findActiveById(Long id);

    @Query("SELECT t FROM Task t WHERE t.title = :title AND t.deletedAt IS NULL")
    Optional<Task> findActiveByTitle(String title);

    @Query("SELECT t FROM Task t WHERE t.deletedAt IS NULL")
    Page<Task> findAllActive(Pageable pageable);

    // TODO: Implementar pesquisa por user/categoria/status futuramente
}