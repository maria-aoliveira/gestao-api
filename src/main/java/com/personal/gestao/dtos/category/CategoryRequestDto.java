package com.personal.gestao.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "Category name is mandatory")
    @Size(max = 100, message = "Category name cannot have more than 100 characters")
    private String name;

}