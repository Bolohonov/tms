package dev.bolohonov.tms.server.services;

import dev.bolohonov.tms.server.dto.CommentDto;
import dev.bolohonov.tms.server.dto.TaskDto;

import java.util.Collection;

public interface CommentService {

    /**+
     * Получить список комментариев к задаче
     */
    Collection<CommentDto> getCommentsByTask(Long taskId, Integer from, Integer size);

    /**
     * Добавить комментарий к задаче
     */
    CommentDto addComment(String userName, Long taskId, CommentDto comment);
}
