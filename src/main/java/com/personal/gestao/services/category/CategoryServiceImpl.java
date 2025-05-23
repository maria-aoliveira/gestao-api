package com.personal.gestao.services.category;

import com.personal.gestao.dtos.category.CategoryPageResponseDto;
import com.personal.gestao.dtos.category.CategoryRequestDto;
import com.personal.gestao.dtos.category.CategoryResponseDto;
import com.personal.gestao.entities.Category;
import com.personal.gestao.exceptions.ResourceNotFoundException;
import com.personal.gestao.mappers.CategoryMapper;
import com.personal.gestao.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.personal.gestao.utils.validation.ValidationUtils.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        validateCreate(categoryRequestDto.getName());
        Category category = CategoryMapper.toCategoryEntity(categoryRequestDto);
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryPageResponseDto listAllCategories(Pageable pageable) {
        Page<Category> category = categoryRepository.findAll(pageable);
        return CategoryPageResponseDto.fromPage(category);
    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = getCategoryEntityById(id);
        validateUpdate(categoryRequestDto.getName(), id);
        category.setName(categoryRequestDto.getName());
        categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        getCategoryEntityById(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponseDto findByCategory(String name){
        Category category = categoryRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Category '" + name + "'not found"));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryResponseDto findCategoryById(Long id) {
        return CategoryMapper.toCategoryDto(getCategoryEntityById(id));
    }

    private Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private void validateCreate(String name){
        validateRequiredField(name, "Category name");
        validateDuplicateOnCreate(name, "Category", categoryRepository::findByName);
    }

    private void validateUpdate(String name, Long id){
        validateRequiredField(name,"Category name");
        validateDuplicateOnUpdate(name, id, "Category", categoryRepository::findByName, Category::getId);
    }
}
