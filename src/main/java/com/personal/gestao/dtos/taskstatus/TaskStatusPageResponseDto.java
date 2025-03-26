package com.personal.gestao.dtos.taskstatus;

import com.personal.gestao.dtos.shared.PageMetaDto;
import com.personal.gestao.entities.TaskStatus;
import com.personal.gestao.mappers.TaskStatusMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusPageResponseDto {
    private List<TaskStatusResponseDto> statuses;
    private PageMetaDto meta;

    public static TaskStatusPageResponseDto fromPage(Page<TaskStatus> page) {
        List<TaskStatusResponseDto> content = page.getContent()
                .stream()
                .map(TaskStatusMapper::toTaskStatusDto)
                .toList();

        PageMetaDto meta = new PageMetaDto(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );

        return new TaskStatusPageResponseDto(content, meta);
    }
}
