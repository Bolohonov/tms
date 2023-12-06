package dev.bolohonov.tms.api.controllers;

import dev.bolohonov.tms.server.dto.CommentDto;
import dev.bolohonov.tms.server.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    @ResponseStatus(OK)
    public ResponseEntity postComment(@PathVariable Long taskId,
                                      @RequestBody CommentDto commentDto,
                                      Principal principal) {
        return ResponseEntity.ok().body(commentService.addComment(principal.getName(), taskId, commentDto));
    }

    @GetMapping()
    @ResponseStatus(OK)
    public ResponseEntity findComments(@PathVariable Long taskId,
                                       @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                       Integer from,
                                       @Positive @RequestParam(name = "size", defaultValue = "50")
                                           Integer size) {
        return ResponseEntity.ok().body(commentService.getCommentsByTask(taskId, from, size));
    }
}
