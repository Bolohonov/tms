package dev.bolohonov.tms.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность пользователя")
public class UserDto {
    /**
     * Имя или логин (email) пользователя
     */
    @Schema(description = "Уникальный логин пользователя (email)")
    @NotBlank
    @Email
    private String name;
}
