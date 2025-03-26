package com.personal.gestao.dtos.user;

import com.personal.gestao.dtos.shared.PageMetaDto;
import com.personal.gestao.entities.User;
import com.personal.gestao.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPageResponseDto {
    private List<UserResponseDto> users;
    private PageMetaDto meta;

    public static UserPageResponseDto fromPage(Page<User> page) {
        List<UserResponseDto> content = page.getContent()
                .stream()
                .map(UserMapper::toUserDto)
                .toList();

        PageMetaDto meta = new PageMetaDto(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );

        return new UserPageResponseDto(content, meta);
    }
}
