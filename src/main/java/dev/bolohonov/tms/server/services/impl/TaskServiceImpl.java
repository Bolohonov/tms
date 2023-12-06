package dev.bolohonov.tms.server.services.impl;

import dev.bolohonov.tms.server.dto.TaskDto;
import dev.bolohonov.tms.server.errors.security.AuthorizationException;
import dev.bolohonov.tms.server.errors.task.TaskNotFoundException;
import dev.bolohonov.tms.server.mappers.TaskMapper;
import dev.bolohonov.tms.server.model.Task;
import dev.bolohonov.tms.server.model.User;
import dev.bolohonov.tms.server.repo.task.TaskRepository;
import dev.bolohonov.tms.server.services.TaskService;
import dev.bolohonov.tms.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.of;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserService userService;

    @Override
    public Collection<TaskDto> getTasks(Integer from, Integer size) {
        Page<Task> tasks = taskRepository.findAll(getPageRequest(from, size));
        return checkAndReturnCollection(tasks);
    }

    @Override
    public Optional<TaskDto> getTaskById(Long taskId) {
        return of(taskMapper.toTaskDto(getDomainTaskById(taskId)));
    }

    @Override
    public Optional<TaskDto> addTask(String username, TaskDto task) {
        taskRepository.save(taskMapper.fromTaskDto(task));
        return of(task);
    }

    @Override
    public Optional<TaskDto> updateTask(Long taskId, String username, TaskDto newTask, Collection<Long> ids) {

        if (isInitiator(taskId, username)) {
            throw new AuthorizationException(String.format("Ошибка при обновлении задачи с id %s", taskId), username);
        }

        Task oldTask = getDomainTaskById(taskId);
        if (ids != null && !ids.isEmpty()) {
            Set<User> users = ids.stream()
                    .map(i -> userService.getDomainUserById(i).get())
                    .collect(Collectors.toSet());
            oldTask.setExecutors(new HashSet<>(users));
        }
        if (!oldTask.equals(newTask)) {
            oldTask.setTitle(newTask.getTitle());
            oldTask.setExecutors(newTask.getExecutors()
                    .stream()
                    .map(u -> userService.getUserByName(u.getName()).get())
                    .collect(Collectors.toSet()));
            oldTask.setState(newTask.getState());
            oldTask.setDescription(newTask.getDescription());
            oldTask.setPriority(newTask.getPriority());
            oldTask.setInitiatorId(newTask.getInitiatorId());
        }
        return of(taskMapper.toTaskDto(oldTask));
    }

    @Override
    public void deleteTask(String username, Long taskId) {
        taskRepository.delete(getDomainTaskById(taskId));
    }

    @Override
    public Collection<TaskDto> getTasksByInitiator(Long userId, Integer from, Integer size) {
        Page<Task> tasks = taskRepository.getTasksByInitiatorId(userId, getPageRequest(from, size));
        return checkAndReturnCollection(tasks);
    }

    @Override
    public Collection<TaskDto> getTasksByExecutor(Long userId, Integer from, Integer size) {

        Collection<Task> tasks = taskRepository
                .getTasksIdsByExecutorId(userId, from, size)
                .stream()
                .map(this::getDomainTaskById)
                .collect(Collectors.toList());

        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }
        return taskMapper.toTaskDto(tasks);
    }

    @Override
    public Optional<TaskDto> updateTaskByExecutor(Long taskId, String username, TaskDto task) {

        if (isInitiator(taskId, username)) {
            return updateTask(taskId, username, task, Collections.emptyList());
        }

        if (!getDomainTaskById(taskId).getExecutors().contains(userService.getDomainUserByName(username).get())) {
            throw new AuthorizationException(String.format("Ошибка при обновлении задачи с id %s", taskId), username);
        }

        Task oldTask = getDomainTaskById(taskId);
        oldTask.setState(task.getState());

        return of(taskMapper.toTaskDto(oldTask));
    }

    private Task getDomainTaskById(Long taskId)  {
        return taskRepository.findById(taskId).orElseThrow(() -> {
            throw new TaskNotFoundException("Ошибка при запросе события", taskId);
        });
    }

    private boolean isInitiator(Long taskId, String username) {
        return getDomainTaskById(taskId).getInitiatorId()
                .equals(userService.getDomainUserByName(username).get().getId());

    }

    private Pageable getPageRequest(Integer from, Integer size) {
        return PageRequest.of(from % size, size);
    }

    private Collection<TaskDto> checkAndReturnCollection(Page<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }
        return taskMapper.toTaskDto(tasks.getContent());
    }
}
