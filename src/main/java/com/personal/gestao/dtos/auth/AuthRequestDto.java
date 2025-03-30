package com.personal.gestao.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {

    @Email(message = "E-mail must be valid")
    @NotBlank(message = "E-mail is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
