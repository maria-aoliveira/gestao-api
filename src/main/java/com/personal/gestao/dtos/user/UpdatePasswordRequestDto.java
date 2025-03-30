package com.personal.gestao.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDto {
    @NotBlank(message = "Current password is mandatory")
    private String currentPassword;

    @NotBlank(message = "New password is mandatory")
    @Size(min = 6, message = "New password must be at least 6 characters")
    private String newPassword;

    @NotBlank(message = "A confirmação de senha é obrigatória")
    private String confirmPassword;
}
