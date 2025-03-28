package com.personal.gestao.repositories;

import com.personal.gestao.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Para validações e criação — podem incluir desativados
    Optional<User> findByUsername(String username);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);

    // Para leitura normal — ignoram usuários desativados
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deactivatedAt IS NULL")
    Optional<User> findActiveById(Long id);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.deactivatedAt IS NULL")
    Optional<User> findActiveByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.name = :name AND u.deactivatedAt IS NULL")
    Optional<User> findActiveByName(String name);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.deactivatedAt IS NULL")
    Optional<User> findActiveByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.deactivatedAt IS NULL")
    Page<User> findAllActive(Pageable pageable);
}
