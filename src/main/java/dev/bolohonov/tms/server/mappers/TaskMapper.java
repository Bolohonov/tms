package dev.bolohonov.tms.server.mappers;

import dev.bolohonov.tms.server.dto.TaskDto;
import dev.bolohonov.tms.server.model.Task;
import dev.bolohonov.tms.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserService userService;

    public TaskDto toTaskDto(Task task) {
        return new TaskDto(
                task.getTitle(),
                task.getDescription(),
                task.getInitiatorId(),
                new HashSet<>(UserMapper.toUserDto(task.getExecutors() != null
                        ? task.getExecutors()
                        : Collections.emptySet())),
                task.getState(),
                task.getPriority()
        );
    }

    public Collection<TaskDto> toTaskDto(Collection<Task> tasks) {
        return tasks.stream()
                .map(e -> toTaskDto(e))
                .collect(Collectors.toList());
    }

    public Task fromTaskDto(TaskDto dto) {
        return new Task(
                null,
                dto.getTitle(),
                dto.getDescription(),
                dto.getInitiatorId(),
                dto.getExecutors().stream().map(u -> userService.getDomainUserByName(u.getName())
                        .get())
                        .collect(Collectors.toSet()),
                dto.getState(),
                dto.getPriority()
        );
    }

    public Collection<Task> fromTaskDto(Collection<TaskDto> dtos) {
        return dtos.stream()
                .map(e -> fromTaskDto(e))
                .collect(Collectors.toList());
    }

}
