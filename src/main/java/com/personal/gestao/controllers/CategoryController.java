package com.personal.gestao.controllers;

import com.personal.gestao.dtos.category.CategoryRequestDto;
import com.personal.gestao.dtos.category.CategoryResponseDto;
import com.personal.gestao.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory (@RequestBody @Valid CategoryRequestDto categoryRequestDto){
        CategoryResponseDto category = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.status(201).body(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        List<CategoryResponseDto> category = categoryService.listAllCategories();
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id){
        CategoryResponseDto category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto categoryRequestDto){
        CategoryResponseDto category = categoryService.updateCategory(id, categoryRequestDto);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-name")
    public ResponseEntity<CategoryResponseDto> findByCategory(@RequestParam String name){
        CategoryResponseDto category = categoryService.findByCategory(name);
        return ResponseEntity.ok(category);
    }
}
