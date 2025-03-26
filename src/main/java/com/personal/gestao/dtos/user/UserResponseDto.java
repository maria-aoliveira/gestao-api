package com.personal.gestao.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String name;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
