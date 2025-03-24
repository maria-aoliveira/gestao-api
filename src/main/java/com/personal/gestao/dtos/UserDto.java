package com.personal.gestao.dtos;

import com.personal.gestao.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

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

    public static UserDto toUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail()
        );
    }

    public User toEntity(){
        User user = new User();
        user.setUsername(this.username);
        user.setName(this.name);
        user.setEmail(this.email);
        return user;
    }
}
