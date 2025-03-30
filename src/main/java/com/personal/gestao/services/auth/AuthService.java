package com.personal.gestao.services.auth;

import com.personal.gestao.dtos.auth.AuthRequestDto;

public interface AuthService {
    String login(AuthRequestDto authRequest);
}
