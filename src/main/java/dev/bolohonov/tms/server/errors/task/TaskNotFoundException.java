package dev.bolohonov.tms.server.errors.task;

import dev.bolohonov.tms.server.errors.ApiError;
import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends ApiError {

    public TaskNotFoundException(String reason, Long id) {
        super(HttpStatus.BAD_REQUEST, String.format("Задача с id %s не найдена", id), reason);
    }

    public TaskNotFoundException(String reason) {
        super(HttpStatus.BAD_REQUEST, "Задачи не найдены", reason);
    }
}
