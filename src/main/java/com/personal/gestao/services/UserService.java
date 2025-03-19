package com.personal.gestao.services;

import com.personal.gestao.dtos.TaskDto;
import com.personal.gestao.dtos.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    List<UserDto> listAllUsers();

    UserDto updateUser(Long id, UserDto user);

    void deleteUser(Long id);

    UserDto findUserById(Long id);

}
