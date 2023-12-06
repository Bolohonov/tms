package dev.bolohonov.tms.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessDto {
    private String token;

    private String type = "Bearer";

    private String username;

    private List<String> roles;
}
