package com.personal.gestao.swagger;

import com.personal.gestao.dtos.category.CategoryPageResponseDto;
import com.personal.gestao.dtos.category.CategoryRequestDto;
import com.personal.gestao.dtos.category.CategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
public interface CategoryApiDocumentation {

    @Operation(
            summary = "Cria uma nova categoria",
            description = "Cria uma categoria com nome único, limitado a 100 caracteres."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou nome duplicado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto);

    @Operation(
            summary = "Lista todas as categorias com paginação",
            description = "Retorna categorias com paginação e ordenação. Considera apenas categorias ativas (se houver soft delete)."
    )
    ResponseEntity<CategoryPageResponseDto> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    );

    @Operation(
            summary = "Busca categoria por ID",
            description = "Retorna a categoria correspondente ao ID informado, se estiver ativa."
    )
    ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id);

    @Operation(
            summary = "Atualiza uma categoria existente",
            description = "Atualiza o nome da categoria. O novo nome deve ser único e válido."
    )
    ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id,
                                                       @RequestBody @Valid CategoryRequestDto categoryRequestDto);

    @Operation(
            summary = "Deleta uma categoria",
            description = "Remove uma categoria do sistema. Só é permitida a exclusão se não houver tarefas associadas."
    )
    ResponseEntity<Void> deleteCategory(@PathVariable Long id);

    @Operation(
            summary = "Busca categoria por nome",
            description = "Retorna a categoria com o nome informado, se estiver ativa."
    )
    ResponseEntity<CategoryResponseDto> findByCategoryName(@RequestParam String name);
}
