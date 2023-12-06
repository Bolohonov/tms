package dev.bolohonov.tms.server.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    /**
     * Пользователь оставивший комментарий
     */
    private Long authorId;

    /**
     * Задача к которой оставлен комментарий
     */
    private Long taskId;

    /**
     * Текст комментария
     */
    private String text;
}
