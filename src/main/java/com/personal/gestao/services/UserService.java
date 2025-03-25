package com.personal.gestao.services;

import com.personal.gestao.dtos.user.UserRequestDto;
import com.personal.gestao.dtos.user.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto user);

    List<UserResponseDto> listAllUsers();

    UserResponseDto updateUser(Long id, UserRequestDto user);

    void deleteUser(Long id);

    UserResponseDto findUserById(Long id);

    UserResponseDto findByUsername(String username);

    UserResponseDto findByName(String name);

    UserResponseDto findByEmail(String email);
}
