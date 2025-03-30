package com.personal.gestao.controllers;

import com.personal.gestao.dtos.user.*;
import com.personal.gestao.services.user.UserService;
import com.personal.gestao.swagger.UserApiDocumentation;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController implements UserApiDocumentation {

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
    public ResponseEntity<UserPageResponseDto> listAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sortOrder = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        UserPageResponseDto users = userService.listAllUsers(pageable);
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
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        UserResponseDto updatedUser = userService.updateUser(id, updateUserRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @RequestBody @Valid UpdatePasswordRequestDto dto) {
        userService.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

}
