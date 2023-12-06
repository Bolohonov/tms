package dev.bolohonov.tms.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "comments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
