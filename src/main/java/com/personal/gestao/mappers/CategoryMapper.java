package com.personal.gestao.mappers;

import com.personal.gestao.dtos.category.CategoryRequestDto;
import com.personal.gestao.dtos.category.CategoryResponseDto;
import com.personal.gestao.entities.Category;

public class CategoryMapper {

    public static CategoryResponseDto toCategoryDto(Category category){
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category toEntity(CategoryRequestDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }
}
