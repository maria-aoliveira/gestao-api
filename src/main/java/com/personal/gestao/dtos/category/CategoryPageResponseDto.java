package com.personal.gestao.dtos.category;

import com.personal.gestao.dtos.shared.PageMetaDto;
import com.personal.gestao.entities.Category;
import com.personal.gestao.mappers.CategoryMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageResponseDto {
    private List<CategoryResponseDto> categories;
    private PageMetaDto meta;

    public static CategoryPageResponseDto fromPage(Page<Category> page) {
        List<CategoryResponseDto> content = page.getContent()
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .toList();

        PageMetaDto meta = new PageMetaDto(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );

        return new CategoryPageResponseDto(content, meta);
    }
}
