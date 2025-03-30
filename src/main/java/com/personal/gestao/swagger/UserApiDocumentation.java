package com.personal.gestao.swagger;

import com.personal.gestao.dtos.user.UserPageResponseDto;
import com.personal.gestao.dtos.user.UserRequestDto;
import com.personal.gestao.dtos.user.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

public interface UserApiDocumentation {

    @Operation(
            summary = "Cria um novo usuário",
            description = "Cria um novo usuário com nome, username e e-mail únicos. Campos obrigatórios: nome, username e e-mail válido. "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou campos duplicados",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto);

    @Operation(
            summary = "Lista todos os usuários ativos com paginação",
            description = "Retorna apenas os usuários que não foram desativados (soft delete)."
    )
    ResponseEntity<UserPageResponseDto> listAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    );

    @Operation(
            summary = "Busca usuário ativo por ID",
            description = "Retorna o usuário se ele estiver ativo (não desativado)."
    )
    ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id);

    @Operation(
            summary = "Busca usuário ativo por username",
            description = "Retorna o usuário com o username informado, se estiver ativo."
    )
    ResponseEntity<UserResponseDto> findUserByUsername(@RequestParam String username);

    @Operation(
            summary = "Busca usuário ativo por nome",
            description = "Retorna o usuário com o nome informado, se estiver ativo."
    )
    ResponseEntity<UserResponseDto> findUserByName(@RequestParam String name);

    @Operation(
            summary = "Busca usuário ativo por e-mail",
            description = "Retorna o usuário com o e-mail informado, se estiver ativo."
    )
    ResponseEntity<UserResponseDto> findUserByEmail(@RequestParam String email);

    @Operation(
            summary = "Atualiza um usuário existente",
            description = "Atualiza os dados de um usuário ativo. Username e e-mail devem permanecer únicos."
    )
    ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                               @RequestBody @Valid UserRequestDto userRequestDto);

    @Operation(
            summary = "Desativa um usuário (soft delete)",
            description = "Marca o usuário como inativo. A operação só é permitida se ele estiver ativo."
    )
    ResponseEntity<Void> deleteUser(@PathVariable Long id);
}
