package dev.bolohonov.tms.server.dto;

import dev.bolohonov.tms.server.model.enums.TaskPriority;
import dev.bolohonov.tms.server.model.enums.TaskState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Сущность задачи")
public class TaskDto {
    /**
     * Заголовок задачи
     */
    @Schema(description = "Заголовок задачи")
    @NotBlank(message = "Заголовок не должен быть пустым")
    private String title;
    /**
     * Полное описание задачи
     */
    @Schema(description = "Полное описание задачи")
    private String description;

    /**
     * Инициатор задачи
     */
    @Schema(description = "Инициатор задачи (логин пользователя)")
    @NotNull(message = "Необходимо указать инициатора")
    private Long initiatorId;
    /**
     * Исполнители задачи
     */
    @Schema(description = "Коллекция исполнителей задачи (без дубликатов пользователей)")
    private Collection<Long> executors;
    /**
     * Список состояний жизненного цикла задачи
     */
    @Schema(description = "Состояния жизненного цикла задачи: " +
            "PENDING - Ожидает утверждения, " +
            "PROGRESS - В процессе исполнения," +
            "COMPLETED - Завершена")
    private TaskState state;
    /**
     * Список приоритетов задачи
     */
    @Schema(description = "Список приоритетов задачи:" +
            "LOW - низкий," +
            "MEDIUM - средний," +
            "HIGH - высокий")
    private TaskPriority priority;
}
