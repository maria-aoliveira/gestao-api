package com.personal.gestao.swagger;

import com.personal.gestao.dtos.task.TaskPageResponseDto;
import com.personal.gestao.dtos.task.TaskRequestDto;
import com.personal.gestao.dtos.task.TaskResponseDto;
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
public interface TaskApiDocumentation {

    @Operation(
            summary = "Cria uma nova tarefa",
            description = "Cria uma tarefa associada a um usuário. O título não pode estar duplicado para o mesmo usuário. " +
                    "Campos opcionais: descrição, data de vencimento, categoria e status."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou título já existente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto taskRequestDTO);

    @Operation(
            summary = "Lista tarefas ativas com paginação e ordenação",
            description = "Retorna apenas tarefas que não foram deletadas (soft delete)."
    )
    ResponseEntity<TaskPageResponseDto> listTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    );

    @Operation(
            summary = "Busca tarefa ativa por ID",
            description = "Retorna a tarefa se ela estiver ativa (não deletada)."
    )
    ResponseEntity<TaskResponseDto> findByTaskId(@PathVariable Long id);

    @Operation(
            summary = "Busca tarefa ativa por título",
            description = "Retorna a tarefa pelo título informado, se ela não estiver deletada."
    )
    ResponseEntity<TaskResponseDto> findByTaskTitle(@RequestParam String title);

    @Operation(
            summary = "Atualiza uma tarefa existente",
            description = "Atualiza uma tarefa ativa (não deletada). O título atualizado não pode repetir outro existente para o mesmo usuário."
    )
    ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id,
                                               @RequestBody @Valid TaskRequestDto taskRequestDTO);

    @Operation(
            summary = "Deleta uma tarefa (soft delete)",
            description = "Marca a tarefa como deletada (não remove do banco). A operação só é permitida se a tarefa estiver ativa."
    )
    ResponseEntity<Void> deleteTask(@PathVariable Long id);
}
