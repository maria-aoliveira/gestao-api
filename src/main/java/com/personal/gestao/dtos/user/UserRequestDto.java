package com.personal.gestao.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Username is mandatory")
    @Size(max = 50, message = "Username cannot have more than 50 characters")
    private String username;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot have more than 100 characters")
    private String name;

    @Email(message = "E-mail must be valid")
    @Size(message = "E-mail must not have more than 255 characters", max = 255)
    @NotBlank(message = "E-mail is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
}
