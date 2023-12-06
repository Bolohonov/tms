package dev.bolohonov.tms.api.controllers;

import dev.bolohonov.tms.server.dto.TaskDto;
import dev.bolohonov.tms.server.errors.mapper.MapperException;
import dev.bolohonov.tms.server.errors.task.TaskNotFoundException;
import dev.bolohonov.tms.server.errors.user.UserRoleUndefinedException;
import dev.bolohonov.tms.server.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.Collection;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {"${tms.origin.localhost}"},
        allowedHeaders = {"Origin", "Authorization", "X-Requested-With", "Content-Type", "Accept", "Cookie"},
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS},
        maxAge = 3600)
public class TaskController {

    private static final String EXECUTOR = "EXECUTOR";
    private static final String INITIATOR = "INITIATOR";

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    @ResponseStatus(OK)
    public Collection<TaskDto> findTasks(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                 Integer from,
                             @Positive @RequestParam(name = "size", defaultValue = "50")
                                 Integer size) {
        return taskService.getTasks(from, size);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(OK)
    public TaskDto findTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId).orElseThrow(
                () -> new MapperException("Ошибка при запросе задачи", taskId));
    }

    @PostMapping()
    @ResponseStatus(OK)
    public TaskDto postTask(@RequestBody TaskDto taskDto, Principal principal) {
        return taskService.addTask(principal.getName(), taskDto).orElseThrow(
                () -> new MapperException("Ошибка при сохранении задачи", taskDto.getTitle())
        );
    }

    @PatchMapping("/{taskId}")
    @ResponseStatus(OK)
    public TaskDto patchTask(@PathVariable Long taskId,
                                    @RequestBody TaskDto taskDto,
                                    @RequestParam(required = false) Collection<Long> users,
                                    Principal principal) {
        return taskService.updateTask(taskId, principal.getName(), taskDto, users).orElseThrow(
                        () -> new MapperException("Ошибка при обновлении задачи", taskId)
        );
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(OK)
    public void removeTask(@PathVariable Long taskId, Principal principal) {
        taskService.deleteTask(principal.getName(), taskId);
    }

    @GetMapping("/role/{userId}")
    @ResponseStatus(OK)
    public Collection<TaskDto> findTasksByUser(@PathVariable Long userId,
                                                  @NotBlank @RequestParam (name = "role") String role,
                                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                                  Integer from,
                                                  @Positive @RequestParam(name = "size", defaultValue = "50")
                                                      Integer size) {
        if (role.equals(EXECUTOR)) {
            return taskService.getTasksByExecutor(userId, from, size);
        }
        if (role.equals(INITIATOR)) {
            return taskService.getTasksByInitiator(userId, from, size);
        }
        throw new UserRoleUndefinedException("Невозможно определить статус пользователя", role);
    }

    @PatchMapping("/role/{taskId}")
    @ResponseStatus(OK)
    public ResponseEntity patchTaskByExecutor(@PathVariable Long taskId,
                                              @RequestBody TaskDto taskDto,
                                              Principal principal) {
        return ResponseEntity.ok().body(taskService.updateTaskByExecutor(taskId, principal.getName(), taskDto));
    }
}
