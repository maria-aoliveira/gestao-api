package com.personal.gestao.services;

import com.personal.gestao.dtos.category.CategoryRequestDto;
import com.personal.gestao.dtos.category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> listAllCategories();

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto);

    void deleteCategory(Long id);

    CategoryResponseDto findCategoryById(Long id);

    CategoryResponseDto findByCategory(String name);
}
