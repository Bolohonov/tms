package dev.bolohonov.tms.server.errors.mapper;

import dev.bolohonov.tms.server.errors.ApiError;
import org.springframework.http.HttpStatus;

public class MapperException extends ApiError {

    public MapperException(String reason, Long id) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Ошибка маппинга", id), reason);
    }

    public MapperException(String reason, String name) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Ошибка маппинга", name), reason);
    }
}
