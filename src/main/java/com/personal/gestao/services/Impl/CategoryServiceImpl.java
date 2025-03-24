package com.personal.gestao.services.Impl;

import com.personal.gestao.dtos.CategoryDto;
import com.personal.gestao.entities.Category;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.repositories.CategoryRepository;
import com.personal.gestao.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        validateCreateUpdate(categoryDto);
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
        Category category = getCategoryEntityById(id);
        validateCreateUpdate(categoryDto);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return CategoryDto.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        getCategoryEntityById(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto findByCategory(String name){
        Category category = categoryRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Category '" + name + "'not found"));
        return CategoryDto.toCategoryDto(category);
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        return CategoryDto.toCategoryDto(getCategoryEntityById(id));
    }


    private Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private void validateCategoryName(String name){
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Category name is mandatory");
        }
    }

    private void validateCreateUpdate(CategoryDto categoryDto){
        validateCategoryName(categoryDto.getName());
        categoryRepository.findByName(categoryDto.getName()).ifPresent(existingCategory -> {
            boolean isNew = categoryDto.getId() == null;
            boolean isDifferent = !existingCategory.getId().equals(categoryDto.getId());

            if (isNew || isDifferent) {
                throw new DataIntegrityViolationException("Category already exists");
            }
        });
    }
}
