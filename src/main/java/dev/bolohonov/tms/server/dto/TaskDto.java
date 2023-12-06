package dev.bolohonov.tms.server.dto;

import dev.bolohonov.tms.server.model.enums.TaskPriority;
import dev.bolohonov.tms.server.model.enums.TaskState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TaskDto {
    /**
     * Заголовок задачи
     */
    private String title;
    /**
     * Полное описание задачи
     */
    private String description;

    /**
     * Инициатор задачи
     */
    private Long initiatorId;
    /**
     * Исполнители задачи
     */
    private Set<UserDto> executors;
    /**
     * Список состояний жизненного цикла задачи
     */
    private TaskState state;
    /**
     * Список приоритетов задачи
     */
    private TaskPriority priority;
}
