package dev.bolohonov.tms.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    /**
     * Имя или логин (email) пользователя
     */
    private String name;
}
