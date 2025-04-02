package com.personal.gestao.repositories;

import com.personal.gestao.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Métodos padrão: Para uso comum (usuário autenticado)

    @Query("SELECT t FROM Task t WHERE t.id = :id AND t.user.id = :userId AND t.deletedAt IS NULL")
    Optional<Task> findActiveByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.title = :title AND t.user.id = :userId AND t.deletedAt IS NULL")
    Optional<Task> findActiveByTitleAndUserId(@Param("title") String title, @Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.deletedAt IS NULL")
    Page<Task> findAllActiveByUserId(@Param("userId") Long userId, Pageable pageable);

    // --- Métodos administrativos: Para uso exclusivo de admin ou serviços internos

    @Query("SELECT t FROM Task t WHERE t.id = :id AND t.deletedAt IS NULL")
    Optional<Task> findActiveById(@Param("id") Long id);

    @Query("SELECT t FROM Task t WHERE t.title = :title AND t.deletedAt IS NULL")
    Optional<Task> findActiveByTitle(@Param("title") String title);

    @Query("SELECT t FROM Task t WHERE t.deletedAt IS NULL")
    Page<Task> findAllActive(Pageable pageable);

    // TODO: Implementar pesquisa por user/categoria/status futuramente
}