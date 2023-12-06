package dev.bolohonov.tms.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность для возврата авторизованному пользователю токена и его ролей")
public class LoginSuccessDto {
    @Schema(description = "Токен")
    private String token;

    @Schema(description = "Носитель")
    private String type = "Bearer";

    @Schema(description = "Логин пользователя")
    private String username;

    @Schema(description = "Список ролей пользователя")
    private List<String> roles;
}
