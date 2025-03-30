package com.personal.gestao.services.user;

import com.personal.gestao.dtos.user.*;
import com.personal.gestao.entities.User;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.mappers.UserMapper;
import com.personal.gestao.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.personal.gestao.utils.validation.ValidationUtils.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        validateCreate(userRequestDto);
        User user = UserMapper.toUserEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
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
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto updateUserRequestDto) {

        User user = getUserEntityById(id);
        validateUpdate(updateUserRequestDto, id);

        user.setUsername(updateUserRequestDto.getUsername());
        user.setName(updateUserRequestDto.getName());
        user.setEmail(updateUserRequestDto.getEmail());

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
    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequestDto dto){
        User user = getUserEntityById(id);
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Nova senha e confirmação não coincidem.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
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

    private void validateFields(String username, String name, String email) {
        validateRequiredField(username, "Username");
        validateRequiredField(name, "Name");
        validateRequiredField(email, "Email");
    }

    private void validateCreate(UserRequestDto dto) {
        validateFields(dto.getUsername(), dto.getName(), dto.getEmail());
        validateDuplicateOnCreate(dto.getUsername(), "Username", userRepository::findActiveByUsername);
        validateDuplicateOnCreate(dto.getEmail(), "Email", userRepository::findActiveByEmail);
    }

    private void validateUpdate(UpdateUserRequestDto dto, Long id) {
        validateFields(dto.getUsername(), dto.getName(), dto.getEmail());
        validateDuplicateOnUpdate(dto.getUsername(), id, "Username", userRepository::findActiveByUsername, User::getId);
        validateDuplicateOnUpdate(dto.getEmail(), id, "Email", userRepository::findActiveByEmail, User::getId);
    }
}
