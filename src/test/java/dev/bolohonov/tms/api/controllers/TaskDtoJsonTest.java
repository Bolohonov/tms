package dev.bolohonov.tms.api.controllers;

import dev.bolohonov.tms.server.dto.TaskDto;
import dev.bolohonov.tms.server.model.enums.TaskPriority;
import dev.bolohonov.tms.server.model.enums.TaskState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureJson
@AutoConfigureJsonTesters
public class TaskDtoJsonTest {

    @Autowired
    private JacksonTester<TaskDto> json;

    @Test
    void testTaskDto() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .title("title1")
                .description("description1")
                .initiatorId(1L)
                .state(TaskState.PENDING)
                .priority(TaskPriority.LOW)
                .build();
        JsonContent<TaskDto> result = json.write(taskDto);

        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo("title1");
        assertThat(result).extractingJsonPathStringValue("$.description")
                .isEqualTo("description1");
        assertThat(result).extractingJsonPathStringValue("$.priority").isEqualTo(TaskPriority.LOW.toString());
        assertThat(result).extractingJsonPathStringValue("$.state").isEqualTo(TaskState.PENDING.toString());
        assertThat(result).extractingJsonPathNumberValue("$.initiatorId").isEqualTo(1);
    }


}
