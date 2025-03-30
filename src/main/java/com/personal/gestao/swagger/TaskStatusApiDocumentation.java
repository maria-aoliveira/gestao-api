package com.personal.gestao.swagger;

import com.personal.gestao.dtos.taskstatus.TaskStatusPageResponseDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusRequestDto;
import com.personal.gestao.dtos.taskstatus.TaskStatusResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

public interface TaskStatusApiDocumentation {

    @Operation(
            summary = "Cria um novo status de tarefa",
            description = "Cria um status único para ser usado nas tarefas. O nome do status deve ser único e ter no máximo 50 caracteres."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Status criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskStatusResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou status duplicado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<TaskStatusResponseDto> createStatus(@RequestBody @Valid TaskStatusRequestDto taskStatusRequestDto);

    @Operation(
            summary = "Lista todos os status com paginação",
            description = "Retorna todos os status disponíveis (ativos), com suporte a paginação e ordenação."
    )
    ResponseEntity<TaskStatusPageResponseDto> listTaskStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    );

    @Operation(
            summary = "Busca status por ID",
            description = "Retorna o status pelo ID informado, se estiver ativo."
    )
    ResponseEntity<TaskStatusResponseDto> findStatusById(@PathVariable Long id);

    @Operation(
            summary = "Atualiza um status existente",
            description = "Atualiza o nome de um status de tarefa. O novo nome deve continuar sendo único."
    )
    ResponseEntity<TaskStatusResponseDto> updateTaskStatus(@PathVariable Long id,
                                                           @RequestBody @Valid TaskStatusRequestDto taskStatusRequestDto);

    @Operation(
            summary = "Deleta um status de tarefa",
            description = "Remove o status do sistema. Só pode ser deletado se não estiver associado a tarefas ativas."
    )
    ResponseEntity<Void> deleteTaskStatus(@PathVariable Long id);

    @Operation(
            summary = "Busca status por nome",
            description = "Retorna o status com o nome informado, se existir e estiver ativo."
    )
    ResponseEntity<TaskStatusResponseDto> getByStatus(@RequestParam String status);
}
