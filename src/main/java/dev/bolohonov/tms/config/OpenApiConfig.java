package dev.bolohonov.tms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System",
                description = "Test Project", version = "1.0.0",
                contact = @Contact(
                        name = "Bolohonov Michael",
                        email = "bolohonov@list.ru",
                        url = "https://github.com/Bolohonov"
                )
        ),
        servers = @Server(url = "${tms.origin.localhost}")
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class OpenApiConfig {
}
