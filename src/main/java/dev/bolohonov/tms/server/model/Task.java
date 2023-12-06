package dev.bolohonov.tms.server.model;

import dev.bolohonov.tms.server.model.enums.TaskPriority;
import dev.bolohonov.tms.server.model.enums.TaskState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Заголовок задачи
     */
    private String title;
    /**
     * Полное описание задачи
     */
    private String description;

    /**
     * Инициатор задачи
     */
    private Long initiatorId;
    /**
     * Исполнители задачи
     */
    @ManyToMany
    @JoinTable(name = "executors_to_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> executors;
    /**
     * Список состояний жизненного цикла задачи
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private TaskState state;
    /**
     * Список приоритетов задачи
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;
}
