package dev.bolohonov.tms;

import dev.bolohonov.tms.server.dto.UserDto;
import dev.bolohonov.tms.server.model.User;
import dev.bolohonov.tms.server.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer implements CommandLineRunner {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Collection<UserDto> users = userService.findAll();

        if (users == null || users.isEmpty()) {
            log.info("Users are empty, creating the default one!");
            createDefaultUsers();
        } else {
            log.info("Users found:");
        }
    }

    private void createDefaultUsers() {
        User user = User.builder()
                .name("admin@tms.ru")
                .password_hash(passwordEncoder.encode("admin"))
                .build();
        userService.saveUser(user);

        user = User.builder()
                .name("user@tms.ru")
                .password_hash(passwordEncoder.encode("user"))
                .build();
        userService.saveUser(user);

        user = User.builder()
                .name("user2@tms.ru")
                .password_hash(passwordEncoder.encode("user2"))
                .build();
        userService.saveUser(user);
    }
}
