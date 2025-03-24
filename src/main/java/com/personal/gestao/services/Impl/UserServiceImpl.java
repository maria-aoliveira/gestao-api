package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.UserDto;
import com.personal.gestao.entities.User;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.repositories.UserRepository;

import com.personal.gestao.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userDto.toEntity();
        userRepository.save(user);

        return UserDto.toUserDto(user);
    }

    @Override
    public List<UserDto> listAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = getUserEntityById(id);

        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        userRepository.save(user);
        return UserDto.toUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        getUserEntityById(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findUserById(Long id) {
        return UserDto.toUserDto(getUserEntityById(id));
    }

    public User getUserEntityById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
