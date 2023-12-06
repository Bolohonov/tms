package dev.bolohonov.tms.server.repo.comment;

import dev.bolohonov.tms.server.model.Comment;
import dev.bolohonov.tms.server.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Получить список комментариев по id задачи (pagination from & size)
     */
    Page<Comment> getCommentsByTaskId(Long id, Pageable pageRequest);
}
