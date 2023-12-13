package dev.bolohonov.tms.api.controllers;

import dev.bolohonov.tms.server.dto.TaskDto;
import dev.bolohonov.tms.server.errors.mapper.MapperException;
import dev.bolohonov.tms.server.errors.user.UserRoleUndefinedException;
import dev.bolohonov.tms.server.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {"${tms.origin.localhost}"},
        allowedHeaders = {"Origin", "Authorization", "X-Requested-With", "Content-Type", "Accept", "Cookie"},
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS},
        maxAge = 3600)
@Tag(name="Контроллер для работы с задачами",
        description="Контроллер для CRUD операций с задачами и получения списков задач по критериям")
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
    @Operation(
            summary = "Поиск задач",
            description = "Поиск всех задач с пагинацией."
    )
    @SecurityRequirement(name = "JWT")
    public Collection<TaskDto> findTasks(@Parameter(description = "Номер начальной страницы")
                                             @PositiveOrZero @RequestParam (name = "from", defaultValue = "0")
                                             Integer from,
                                         @Parameter(description = "Количество элементов на странице")
                                         @Positive @RequestParam(name = "size", defaultValue = "50")
                                         Integer size) {
        return taskService.getTasks(from, size);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(OK)
    @Operation(
            summary = "Поиск задачи",
            description = "Поиск задачи по идентификатору"
    )
    @SecurityRequirement(name = "JWT")
    public TaskDto findTaskById(@Parameter(description = "Идентификатор задачи") @PathVariable Long taskId) {
        return taskService.getTaskById(taskId).orElseThrow(
                () -> new MapperException("Ошибка при запросе задачи", taskId));
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    @Operation(
            summary = "Добавление задачи",
            description = "Добавление задачи"
    )
    @SecurityRequirement(name = "JWT")
    public TaskDto postTask(@Validated @RequestBody TaskDto taskDto, Principal principal) {
        return taskService.addTask(principal.getName(), taskDto).orElseThrow(
                () -> new MapperException("Ошибка при сохранении задачи", taskDto.getTitle())
        );
    }

    @PatchMapping("/{taskId}")
    @ResponseStatus(OK)
    @Operation(
            summary = "Обновление задачи",
            description = "В зависимости от того, кто является инициатором обновления могут быть обновлены либо " +
                    "все поля задачи (обновляет инициатор), либо только статус (обновляет исполнитель)"
    )
    @SecurityRequirement(name = "JWT")
    public TaskDto patchTask(@Parameter(description = "Идентификатор задачи") @PathVariable Long taskId,
                             @Validated @RequestBody TaskDto taskDto,
                             Principal principal) {
        return taskService.updateTask(taskId, principal.getName(), taskDto).orElseThrow(
                        () -> new MapperException("Ошибка при обновлении задачи", taskId)
        );
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(OK)
    @Operation(
            summary = "Удаление задачи по идентификатору",
            description = "Попытка удаления задачи не инициатором приведет к exception"
    )
    @SecurityRequirement(name = "JWT")
    public void removeTask(@Parameter(description = "Идентификатор задачи")
                               @PathVariable Long taskId, Principal principal) {
        taskService.deleteTask(principal.getName(), taskId);
    }

    @GetMapping("/search/{userId}")
    @ResponseStatus(OK)
    @Operation(
            summary = "Поиск списка задач исполнителей или инициаторов",
            description = "Поиск списка задач закрепленных за исполнителем, " +
                    "либо созданных инициатором в зависимости от параметра role"
    )
    @SecurityRequirement(name = "JWT")
    public Collection<TaskDto> findTasksByUser(@Parameter(description = "Идентификатор пользователя")
                                                   @PathVariable Long userId,
                                               @Parameter(description = "Параметр со значением с или INITIATOR, " +
                                                       "в зависимости от указанного параметра осуществляется поиск " +
                                                       "задач назначенных исполнителю EXECUTOR " +
                                                       "или задач созданных пользователем INITIATOR")
                                               @NotBlank @RequestParam (name = "role") String role,
                                               @Parameter(description = "Номер начальной страницы")
                                                   @PositiveOrZero @RequestParam (name = "from", defaultValue = "0")
                                                   Integer from,
                                               @Parameter(description = "Количество элементов на странице")
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
}
