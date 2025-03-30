package com.personal.gestao.services.user;

import com.personal.gestao.dtos.user.*;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDto createUser(UserRequestDto user);

    UserPageResponseDto listAllUsers(Pageable pageable);

    UserResponseDto updateUser(Long id, UpdateUserRequestDto updateUserRequestDto);

    void updatePassword(Long id, UpdatePasswordRequestDto updatePasswordRequestDto);

    void deleteUser(Long id);

    UserResponseDto findUserById(Long id);

    UserResponseDto findByUsername(String username);

    UserResponseDto findByName(String name);

    UserResponseDto findByEmail(String email);
}
