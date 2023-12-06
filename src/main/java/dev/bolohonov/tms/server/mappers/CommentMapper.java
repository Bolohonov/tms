package dev.bolohonov.tms.server.mappers;

import dev.bolohonov.tms.server.dto.CommentDto;
import dev.bolohonov.tms.server.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getAuthorId(),
                comment.getTaskId(),
                comment.getText()
        );
    }

    public static Collection<CommentDto> toCommentDto(Collection<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public static Comment fromCommentDto(CommentDto commentDto) {
        return new Comment(
                null,
                commentDto.getAuthorId(),
                commentDto.getTaskId(),
                commentDto.getText()
        );
    }
}
