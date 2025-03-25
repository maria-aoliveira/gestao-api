package com.personal.gestao.mappers;

import com.personal.gestao.dtos.user.UserRequestDto;
import com.personal.gestao.dtos.user.UserResponseDto;
import com.personal.gestao.entities.User;

public class UserMapper {


    public static UserResponseDto toUserDto(User user){
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static User toEntity(UserRequestDto userRequestDto){
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        return user;
    }
}
