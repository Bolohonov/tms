package dev.bolohonov.tms.server.errors.user;

import dev.bolohonov.tms.server.errors.ApiError;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiError {
    public UserNotFoundException(String reason, Long id) {
        super(HttpStatus.BAD_REQUEST, String.format("Пользователь с id %s не найден", id), reason);
    }

    public UserNotFoundException(String reason, String name) {
        super(HttpStatus.BAD_REQUEST, String.format("Пользователь с именем %s не найден", name), reason);
    }
}
