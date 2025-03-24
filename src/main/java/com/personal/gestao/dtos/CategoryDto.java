package com.personal.gestao.dtos;

import com.personal.gestao.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Category name is mandatory")
    @Size(max = 100, message = "Category name cannot have more than 100 characters")
    private String name;

    public static CategoryDto toCategoryDto (Category category){
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public Category toEntity() {
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }
}