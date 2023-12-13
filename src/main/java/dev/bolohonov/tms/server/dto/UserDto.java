package dev.bolohonov.tms.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Необходимо ввести логин (минимум 5 символов)")
    @Email(message = "Используйте email в качестве логина")
    @Size(min=5, message = "Логин минимум 5 символов")
    private String name;
}
