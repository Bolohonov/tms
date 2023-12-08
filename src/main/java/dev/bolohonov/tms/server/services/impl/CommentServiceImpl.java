package dev.bolohonov.tms.server.services.impl;

import dev.bolohonov.tms.server.dto.CommentDto;
import dev.bolohonov.tms.server.mappers.CommentMapper;
import dev.bolohonov.tms.server.model.Comment;
import dev.bolohonov.tms.server.repo.comment.CommentRepository;
import dev.bolohonov.tms.server.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Collection<CommentDto> getCommentsByTask(Long taskId, Integer from, Integer size) {
        Page<Comment> comments = commentRepository.getCommentsByTaskId(taskId, getPageRequest(from, size));
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }
        return CommentMapper.toCommentDto(comments.getContent());
    }

    @Override
    public CommentDto addComment(String userName, Long taskId, CommentDto comment) {
        commentRepository.save(CommentMapper.fromCommentDto(comment));
        return comment;
    }

    private Pageable getPageRequest(Integer from, Integer size) {
        return PageRequest.of(from, size);
    }
}
