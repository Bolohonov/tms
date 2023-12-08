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
import java.util.LinkedList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static java.util.Optional.of;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private LinkedList<TaskDto> tasks = new LinkedList<>();

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

        TaskDto t;

        for (int i = 0; i < 10; i++) {
            t = TaskDto.builder()
                    .title("title")
                    .description("description")
                    .initiatorId(1L)
                    .executors(new HashSet<>())
                    .priority(TaskPriority.LOW)
                    .state(TaskState.PENDING)
                    .build();
            tasks.add(t);
        }

    }

    @Test
    @SneakyThrows
    void findTasks() {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/tasks/all")
                .principal(mockPrincipal);

        when(taskService.getTasks(anyInt(), anyInt()))
                .thenReturn(tasks);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
        ;
    }

    @Test
    @SneakyThrows
    void findTaskById() {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/tasks/1")
                .principal(mockPrincipal);

        when(taskService.getTaskById(anyLong()))
                .thenReturn(of(taskDto));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(taskDto.getTitle())))
                .andExpect(jsonPath("$.description", is(taskDto.getDescription())))
                .andExpect(jsonPath("$.state", is(taskDto.getState().toString())))
                .andExpect(jsonPath("$.priority", is(taskDto.getPriority().toString())))
                .andExpect(jsonPath("$.executors", hasSize(0)))
                .andExpect(jsonPath("$.initiatorId", is(taskDto.getInitiatorId()), Long.class));
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(taskDto.getTitle())))
                .andExpect(jsonPath("$.description", is(taskDto.getDescription())))
                .andExpect(jsonPath("$.state", is(taskDto.getState().toString())))
                .andExpect(jsonPath("$.priority", is(taskDto.getPriority().toString())))
                .andExpect(jsonPath("$.executors", hasSize(0)))
                .andExpect(jsonPath("$.initiatorId", is(taskDto.getInitiatorId()), Long.class));
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