package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.CategoryDto;
import com.personal.gestao.entities.Category;
import com.personal.gestao.repositories.CategoryRepository;
import com.personal.gestao.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryDto.toEntity();
        category = categoryRepository.save(category);
        return CategoryDto.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> listAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDto::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDto.getName());
        categoryRepository.save(category);

        return CategoryDto.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryDto.toCategoryDto(category);
    }
}
