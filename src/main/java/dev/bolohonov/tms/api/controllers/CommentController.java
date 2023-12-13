package dev.bolohonov.tms.api.controllers;

import dev.bolohonov.tms.server.dto.CommentDto;
import dev.bolohonov.tms.server.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
@CrossOrigin(origins = {"${tms.origin.localhost}"},
        allowedHeaders = {"Origin", "Authorization", "X-Requested-With", "Content-Type", "Accept", "Cookie"},
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS},
        maxAge = 3600)
@Tag(name="Контроллер для работы с комментариями",
        description="Контроллер для добавления комментариев и получения списка комментариев задачи")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    @ResponseStatus(OK)
    @Operation(
            summary = "Добавление комментария",
            description = "Добавление комментария к задаче по идентификатору"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity postComment(@Parameter(description = "Идентификатор задачи") @PathVariable Long taskId,
                                      @Validated @RequestBody CommentDto commentDto,
                                      Principal principal) {
        return ResponseEntity.ok().body(commentService.addComment(principal.getName(), taskId, commentDto));
    }

    @GetMapping()
    @ResponseStatus(OK)
    @Operation(
            summary = "Поиск комментариев",
            description = "Поиск всех комментариев к задаче по идентификатору"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity findComments(@Parameter(description = "Идентификатор задачи") @PathVariable Long taskId,
                                       @Parameter(description = "Номер начальной страницы")
                                       @PositiveOrZero @RequestParam (name = "from", defaultValue = "0")
                                       Integer from,
                                       @Parameter(description = "Количество элементов на странице")
                                           @Positive @RequestParam(name = "size", defaultValue = "50")
                                           Integer size) {
        return ResponseEntity.ok().body(commentService.getCommentsByTask(taskId, from, size));
    }
}
