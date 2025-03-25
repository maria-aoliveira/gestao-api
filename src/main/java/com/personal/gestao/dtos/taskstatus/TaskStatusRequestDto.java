package com.personal.gestao.dtos.taskstatus;

import com.personal.gestao.entities.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusRequestDto {

    @NotBlank(message = "Status is mandatory")
    @Size(max = 50, message = "Status cannot have more than 50 characters")
    private String status;

}
