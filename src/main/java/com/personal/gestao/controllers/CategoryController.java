package com.personal.gestao.controllers;

import com.personal.gestao.dtos.category.CategoryPageResponseDto;
import com.personal.gestao.dtos.category.CategoryRequestDto;
import com.personal.gestao.dtos.category.CategoryResponseDto;
import com.personal.gestao.services.category.CategoryService;
import com.personal.gestao.swagger.CategoryApiDocumentation;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController implements CategoryApiDocumentation {

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
    public ResponseEntity<CategoryPageResponseDto> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sortOrder = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        CategoryPageResponseDto response = categoryService.listAllCategories(pageable);
        return ResponseEntity.ok(response);
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
    public ResponseEntity<CategoryResponseDto> findByCategoryName(@RequestParam String name){
        CategoryResponseDto category = categoryService.findByCategory(name);
        return ResponseEntity.ok(category);
    }
}
