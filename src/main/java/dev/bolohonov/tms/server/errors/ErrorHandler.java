package dev.bolohonov.tms.server.errors;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ErrorResponse handleApiError(final ApiError e, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        e.getSubErrors().forEach(error -> sb.append(error.toString()));
        response.setStatus(e.getStatus().value());
        return ErrorResponse.builder()
                .id(UUID.randomUUID().toString())
                .errors(sb.toString())
                .message(e.getMessage())
                .reason(e.getReason())
                .status(e.getStatus().toString())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ExceptionHandler
    public ErrorResponse handleInternalServerError(final Throwable e, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ErrorResponse.builder()
                .id(UUID.randomUUID().toString())
                .errors(sb.toString())
                .message(e.getMessage())
                .reason("Произошла ошибка на сервере")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                               HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ErrorResponse.builder()
                .id(UUID.randomUUID().toString())
                .errors(sb.toString())
                .message(e.getMessage())
                .reason("Произошла ошибка на сервере")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}