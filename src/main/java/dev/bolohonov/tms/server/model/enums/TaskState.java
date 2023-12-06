package dev.bolohonov.tms.server.model.enums;

public enum TaskState {
    /**
     * Ожидает утверждения
     */
    PENDING,
    /**
     * В процессе исполнения
     */
    PROGRESS,
    /**
     * Завершена
     */
    COMPLETED
}
