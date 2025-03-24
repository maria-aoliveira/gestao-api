package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.UserDto;
import com.personal.gestao.entities.User;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.repositories.UserRepository;

import com.personal.gestao.services.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        validateCreateUpdate(userDto);
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
        userDto.setId(id);
        User user = getUserEntityById(id);
        validateCreateUpdate(userDto);

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

    @Override
    public UserDto findByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(
                () ->  new ResourceNotFoundException("User not found"));
        return UserDto.toUserDto(user);
    }

    @Override
    public UserDto findByName(String name){
        User user = userRepository.findByName(name).orElseThrow(
                () ->  new ResourceNotFoundException("User not found"));
        return UserDto.toUserDto(user);
    }

    @Override
    public UserDto findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
                () ->  new ResourceNotFoundException("User not found"));
        return UserDto.toUserDto(user);
    }

    private User getUserEntityById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    private void validateUserData(UserDto userDto){
        if (userDto.getUsername() == null || userDto.getUsername().isBlank()){
            throw new IllegalArgumentException("Username is mandatory");
        }

        if (userDto.getName() == null || userDto.getName().isBlank()){
            throw new IllegalArgumentException("Name is mandatory");
        }

        if (userDto.getEmail() == null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("E-mail is mandatory");
        }
    }

    private void validateCreateUpdate(UserDto userDto){
        validateUserData(userDto);
        validateDuplicateEmail(userDto);
        validateDuplicateName(userDto);
        validateDuplicateUsername(userDto);
    }

    private void validateDuplicateUsername(UserDto userDto) {
        checkDuplicateField(userDto.getUsername(), userDto.getId(), "Username", userRepository::findByUsername);
    }

    private void validateDuplicateName(UserDto userDto) {
        checkDuplicateField(userDto.getName(), userDto.getId(), "Name", userRepository::findByName);
    }

    private void validateDuplicateEmail(UserDto userDto) {
        checkDuplicateField(userDto.getEmail(), userDto.getId(), "Email", userRepository::findByEmail);
    }

    private void checkDuplicateField(String value, Long currentId, String fieldName,
                                     Function<String, Optional<User>> finder) {
        finder.apply(value).ifPresent(existingUser -> {
            boolean isNew = currentId == null;
            boolean isDifferent = !existingUser.getId().equals(currentId);
            if (isNew || isDifferent) {
                throw new DataIntegrityViolationException(fieldName + " already exists");
            }
        });
    }
}
