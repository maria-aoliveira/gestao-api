package com.personal.gestao.dtos;

import com.personal.gestao.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String name;
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
