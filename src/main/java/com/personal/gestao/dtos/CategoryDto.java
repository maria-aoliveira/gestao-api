package com.personal.gestao.dtos;

import com.personal.gestao.entities.Category;
import com.personal.gestao.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;
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