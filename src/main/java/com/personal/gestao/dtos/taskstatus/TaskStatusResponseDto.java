package com.personal.gestao.dtos.taskstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusResponseDto {
    private Long id;
    private String status;
}
