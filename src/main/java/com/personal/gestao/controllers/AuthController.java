package com.personal.gestao.controllers;



import com.personal.gestao.dtos.auth.AuthRequestDto;
import com.personal.gestao.dtos.auth.AuthResponseDto;
import com.personal.gestao.repositories.UserRepository;
import com.personal.gestao.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

//    @GetMapping("/debug")
//    public ResponseEntity<?> debugUser(@RequestParam String email) {
//        return ResponseEntity.ok(userRepository.findByEmail(email));
//    }

//    @GetMapping("/debug-password")
//    public ResponseEntity<?> testPassword(@RequestParam String raw, @RequestParam String email) {
//        var user = userRepository.findByEmail(email).orElseThrow();
//        boolean match = passwordEncoder.matches(raw, user.getPassword());
//        return ResponseEntity.ok("Senha confere? " + match);
//    }
//
//    @GetMapping("/test-user")
//    public ResponseEntity<?> test() {
//        var user = userRepository.findByEmail("admin@email.com").orElseThrow();
//        return ResponseEntity.ok(Map.of(
//                "email", user.getEmail(),
//                "encodedPass", user.getPassword(),
//                "enabled", user.isEnabled()
//        ));
//    }


}