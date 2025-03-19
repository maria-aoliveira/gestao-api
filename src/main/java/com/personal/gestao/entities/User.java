package com.personal.gestao.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String password;

    private String email;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
