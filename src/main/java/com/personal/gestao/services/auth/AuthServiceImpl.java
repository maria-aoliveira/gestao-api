package com.personal.gestao.services.auth;

import com.personal.gestao.config.security.AuthenticatedUser;
import com.personal.gestao.config.security.JWTUtils;
import com.personal.gestao.dtos.auth.AuthRequestDto;
import com.personal.gestao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder; // injeta o encoder

    private final AuthenticationManager authManager;
    private final JWTUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authManager, JWTUtils jwtUtils) {
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String login(AuthRequestDto authRequest) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            AuthenticatedUser user = (AuthenticatedUser) auth.getPrincipal();
            return jwtUtils.generateToken(user.getId(), user.getUsername());

        } catch (Exception e) {
            System.out.println("Error while authenticating: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
