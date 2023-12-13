package dev.bolohonov.tms.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    /**
     * Пользователь оставивший комментарий
     */
    @NotNull(message = "Необходимо указать автора")
    private Long authorId;

    /**
     * Задача к которой оставлен комментарий
     */
    @NotNull(message = "Необходимо указать задачу")
    private Long taskId;

    /**
     * Текст комментария
     */
    @NotBlank(message = "Комментарий не должен быть пустым")
    private String text;
}
