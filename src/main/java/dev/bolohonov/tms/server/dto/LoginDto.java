package dev.bolohonov.tms.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Сущность для преобразования json в объект с логином и паролем пользователя")
public class LoginDto {

    private String username;

    private String password;
}
