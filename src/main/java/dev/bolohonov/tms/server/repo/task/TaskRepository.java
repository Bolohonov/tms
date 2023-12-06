package dev.bolohonov.tms.server.repo.task;

import dev.bolohonov.tms.server.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Получить список задач по id инициатора (pagination from & size)
     */
    Page<Task> getTasksByInitiatorId(Long id, Pageable pageRequest);

    /**
     * Получить список задач по id исполнителя (pagination from & size)
     */
    @Query(value = "SELECT task_id FROM executors_to_tasks WHERE user_id = ?1 ORDER BY task_id LIMIT ?3 OFFSET ?2",
            nativeQuery = true)
    Collection<Long> getTasksIdsByExecutorId(Long id, Integer from, Integer size);
}
