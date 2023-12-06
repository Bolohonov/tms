package dev.bolohonov.tms;

import dev.bolohonov.tms.server.dto.TaskDto;
import dev.bolohonov.tms.server.dto.UserDto;
import dev.bolohonov.tms.server.model.Task;
import dev.bolohonov.tms.server.model.User;
import dev.bolohonov.tms.server.model.enums.TaskPriority;
import dev.bolohonov.tms.server.model.enums.TaskState;
import dev.bolohonov.tms.server.repo.task.TaskRepository;
import dev.bolohonov.tms.server.services.TaskService;
import dev.bolohonov.tms.server.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer implements CommandLineRunner {

    private final UserService userService;

    private final TaskRepository taskRepository;

    private final TaskService taskService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Collection<UserDto> users = userService.findAll();

        if (users == null || users.size() == 0) {
            log.info("Users are empty, creating the default one!");
            createDefaultUsers();
        } else {
            log.info("Users found:");
        }

        Collection<Task> tasks = taskRepository.findAll();

        if (tasks == null || tasks.size() == 0) {
            log.info("tasks are empty, creating the default one!");
            createDefaultTasks();
        } else {
            log.info("tasks found:");
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

    private void createDefaultTasks() {
        Set<UserDto> set = new HashSet<>();
        set.add(userService.getUserByName("user@tms.ru").get());
        set.add(userService.getUserByName("user2@tms.ru").get());


        for (int i = 1; i < 25; i++) {
            taskService.addTask("admin@tms.ru",
                    new TaskDto("title" + i, "desc" + i,
                            userService.getDomainUserByName("admin@tms.ru").get().getId(),
                            set, TaskState.PENDING, TaskPriority.LOW));
        }
        set.clear();
        set.add(userService.getUserByName("admin@tms.ru").get());
        set.add(userService.getUserByName("user2@tms.ru").get());

        for (int i = 26; i < 50; i++) {
            taskService.addTask("user@tms.ru",
                    new TaskDto("title" + i, "desc" + i,
                            userService.getDomainUserByName("user@tms.ru").get().getId(),
                            set, TaskState.PROGRESS, TaskPriority.MEDIUM));
        }

        set.clear();
        set.add(userService.getUserByName("admin@tms.ru").get());
        set.add(userService.getUserByName("user@tms.ru").get());

        for (int i = 51; i < 75; i++) {
            taskService.addTask("user2@tms.ru",
                    new TaskDto("title" + i, "desc" + i,
                            userService.getDomainUserByName("user2@tms.ru").get().getId(),
                            set, TaskState.COMPLETED, TaskPriority.HIGH));
        }
    }
}
