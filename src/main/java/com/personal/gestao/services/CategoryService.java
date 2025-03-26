package com.personal.gestao.services;

import com.personal.gestao.dtos.category.CategoryPageResponseDto;
import com.personal.gestao.dtos.category.CategoryRequestDto;
import com.personal.gestao.dtos.category.CategoryResponseDto;
import org.springframework.data.domain.Pageable;


public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryPageResponseDto listAllCategories(Pageable pageable);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto);

    void deleteCategory(Long id);

    CategoryResponseDto findCategoryById(Long id);

    CategoryResponseDto findByCategory(String name);
}
