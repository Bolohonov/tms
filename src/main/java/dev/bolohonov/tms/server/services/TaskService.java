package dev.bolohonov.tms.server.services;

import dev.bolohonov.tms.server.dto.TaskDto;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    /**+
     * Получить все задачи
     */
    Collection<TaskDto> getTasks(Integer from, Integer size);


    /**
     * Получить задачу по Id
     */
    Optional<TaskDto> getTaskById(Long eventId);

    /**
     * Добавить задачу
     */
    Optional<TaskDto> addTask(String username, TaskDto task);

    /**
     * Инициатор обновляет задачу и (или) назначает исполнителей
     */
    Optional<TaskDto> updateTask(Long taskId, String username, TaskDto taskDto, Collection<Long> users);

    /**
     * Удалить задачу
     */
    void deleteTask(String username, Long taskId);

    /**+
     * Получить список событий по id инициатора
     */
    Collection<TaskDto> getTasksByInitiator(Long userId, Integer from, Integer size);

    /**+
     * Получить список событий по id исполнителя
     */
    Collection<TaskDto> getTasksByExecutor(Long userId, Integer from, Integer size);

    /**
     * Инициатор обновляет задачу
     */
    Optional<TaskDto> updateTaskByExecutor(Long taskId, String username, TaskDto task);
}
