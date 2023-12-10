package dev.bolohonov.tms.api.controllers;

import dev.bolohonov.tms.server.model.Task;
import dev.bolohonov.tms.server.model.User;
import dev.bolohonov.tms.server.model.enums.TaskPriority;
import dev.bolohonov.tms.server.model.enums.TaskState;
import dev.bolohonov.tms.server.repo.task.TaskRepository;
import dev.bolohonov.tms.server.repo.user.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
public class TaskJpaTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private LinkedList<User> users = new LinkedList<>();

    private long userId;
    private List<Long> expectedGetTasksIdsByExecutorIdSuccess = new ArrayList<>();
    private List<Long> getTasksIdsByExecutorIdSuccessPageOne = new ArrayList<>();

    int page = 1;
    int size = 3;


    @BeforeEach
    void setUp() {
        Task t;
        for (int i = 1; i <= 5; i++) {
            User user = User.builder()
                    .name("user" + i)
                    .password_hash(String.valueOf(("user" + i).hashCode()))
                    .build();
            users.add(user);
            userRepository.save(user);
            if (i == 1) userId = user.getId();
        }

        for (int i = 1; i <= 5; i++) {
            t = Task.builder()
                    .title("title" + i)
                    .description("description" + i)
                    .initiatorId(1L)
                    .executors(new HashSet<>(Arrays.asList(users.get(i - 1))))
                    .priority(TaskPriority.LOW)
                    .state(TaskState.PENDING)
                    .build();
            taskRepository.save(t);
            if (i == 1) expectedGetTasksIdsByExecutorIdSuccess.add(t.getId());
        }

        for (int i = 2; i < 6; i++) {
            t = Task.builder()
                    .title("title" + i)
                    .description("description" + i)
                    .initiatorId(2L)
                    .executors(new HashSet<>(Arrays.asList(users.get(0), users.get(i - 1))))
                    .priority(TaskPriority.LOW)
                    .state(TaskState.PENDING)
                    .build();
            taskRepository.save(t);
            expectedGetTasksIdsByExecutorIdSuccess.add(t.getId());
            if (i > size) {
                getTasksIdsByExecutorIdSuccessPageOne.add(t.getId());
            }
        }
    }

    @AfterEach
    void cleanUp() {
        users.clear();
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @SneakyThrows
    void getTasksIdsByExecutorIdSuccess() {
        Collection<Long> result = taskRepository.getTasksIdsByExecutorId(userId, 0, 15);
        assertNotEquals(0, userId);
        assertNotEquals(0, result.size());
        assertEquals(expectedGetTasksIdsByExecutorIdSuccess, result);
    }

    @Test
    @SneakyThrows
    void getTasksIdsByExecutorIdSuccessPageOne() {
        Collection<Long> result = taskRepository.getTasksIdsByExecutorId(userId, page * size, size);
        assertNotEquals(0, userId);
        assertNotEquals(0, result.size());
        assertEquals(getTasksIdsByExecutorIdSuccessPageOne, result);
    }


}
