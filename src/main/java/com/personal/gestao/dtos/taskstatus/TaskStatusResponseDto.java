package com.personal.gestao.dtos.taskstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusResponseDto {
    private Long id;
    private String status;
}
