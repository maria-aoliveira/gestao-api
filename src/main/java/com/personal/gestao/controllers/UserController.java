package com.personal.gestao.controllers;

import com.personal.gestao.dtos.user.UserRequestDto;
import com.personal.gestao.dtos.user.UserResponseDto;
import com.personal.gestao.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto user = userService.createUser(userRequestDto);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listAllUsers() {
        List<UserResponseDto> users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        UserResponseDto user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-username")
    public ResponseEntity<UserResponseDto> findUserByUsername(@RequestParam String username) {
        UserResponseDto user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-name")
    public ResponseEntity<UserResponseDto> findUserByName(@RequestParam String name) {
        UserResponseDto user = userService.findByName(name);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDto> findUserByEmail(@RequestParam String email) {
        UserResponseDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
