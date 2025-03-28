package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.user.UserPageResponseDto;
import com.personal.gestao.dtos.user.UserRequestDto;
import com.personal.gestao.dtos.user.UserResponseDto;
import com.personal.gestao.entities.User;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.mappers.UserMapper;
import com.personal.gestao.repositories.UserRepository;

import com.personal.gestao.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.personal.gestao.utils.validation.ValidationUtils.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        validateCreate(userRequestDto);
        User user = UserMapper.toUserEntity(userRequestDto);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserPageResponseDto listAllUsers(Pageable pageable) {
        Page<User> user = userRepository.findAllActive(pageable);
        return UserPageResponseDto.fromPage(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {

        User user = getUserEntityById(id);
        validateUpdate(userRequestDto, id);

        user.setUsername(userRequestDto.getUsername());
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());

        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserEntityById(id);
        user.setDeactivatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        return UserMapper.toUserDto(getUserEntityById(id));
    }

    @Override
    public UserResponseDto findByUsername(String username){
        User user = userRepository.findActiveByUsername(username).orElseThrow(
                () ->  new ResourceNotFoundException("User not found"));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserResponseDto findByName(String name){
        User user = userRepository.findActiveByName(name).orElseThrow(
                () ->  new ResourceNotFoundException("User not found"));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserResponseDto findByEmail(String email){
        User user = userRepository.findActiveByEmail(email).orElseThrow(
                () ->  new ResourceNotFoundException("User not found"));
        return UserMapper.toUserDto(user);
    }

    private User getUserEntityById(Long id){
        return userRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void validateRequiredFields(UserRequestDto dto) {
        validateRequiredField(dto.getUsername(), "Username");
        validateRequiredField(dto.getName(), "Name");
        validateRequiredField(dto.getEmail(), "Email");
    }

    private void validateCreate(UserRequestDto userRequestDto) {
        validateRequiredFields(userRequestDto);
        validateDuplicateOnCreate(userRequestDto.getUsername(), "Username", userRepository::findActiveByUsername);
        validateDuplicateOnCreate(userRequestDto.getEmail(), "Email", userRepository::findActiveByEmail);
    }

    private void validateUpdate(UserRequestDto dto, Long id) {
        validateRequiredFields(dto);
        validateDuplicateOnUpdate(dto.getUsername(), id, "Username", userRepository::findActiveByUsername, User::getId);
        validateDuplicateOnUpdate(dto.getEmail(), id, "Email", userRepository::findActiveByEmail, User::getId);
    }
}
