package dev.bolohonov.tms.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bolohonov.tms.server.dto.TaskDto;
import dev.bolohonov.tms.server.model.enums.TaskPriority;
import dev.bolohonov.tms.server.model.enums.TaskState;
import dev.bolohonov.tms.server.services.TaskService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static java.util.Optional.of;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private final String TMS_ORIGIN_LOCALHOST = "http://localhost:8080";

    Principal mockPrincipal = Mockito.mock(Principal.class);

    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(taskController)
                .addPlaceholderValue("tms.origin.localhost", TMS_ORIGIN_LOCALHOST)
                .build();

        Mockito.when(mockPrincipal.getName()).thenReturn("user");

        taskDto = TaskDto.builder()
                .title("title")
                .description("description")
                .initiatorId(1L)
                .executors(new HashSet<>())
                .priority(TaskPriority.LOW)
                .state(TaskState.PENDING)
                .build();
    }

    @Test
    void findTasks() {
    }

    @Test
    void findTaskById() {
    }

    @Test
    @SneakyThrows
    void postTask() {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/tasks")
                .principal(mockPrincipal)
                .content(mapper.writeValueAsString(taskDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        when(taskService.addTask(anyString(), any()))
                .thenReturn(of(taskDto));

        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    void patchTask() {
    }

    @Test
    void removeTask() {
    }

    @Test
    void findTasksByUser() {
    }
}