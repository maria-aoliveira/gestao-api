package com.personal.gestao.dtos.task;

import com.personal.gestao.dtos.shared.PageMetaDto;
import com.personal.gestao.entities.Task;
import com.personal.gestao.mappers.TaskMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskPageResponseDto {
    private List<TaskResponseDto> tasks;
    private PageMetaDto meta;

    public static TaskPageResponseDto fromPage(Page<Task> page) {
        List<TaskResponseDto> content = page.getContent()
                .stream()
                .map(TaskMapper::toTaskDto)
                .toList();

        PageMetaDto meta = new PageMetaDto(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );

        return new TaskPageResponseDto(content, meta);
    }
}
