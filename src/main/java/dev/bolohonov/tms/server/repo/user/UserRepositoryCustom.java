package dev.bolohonov.tms.server.repo.user;

import dev.bolohonov.tms.server.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserRepositoryCustom {
    /**
     * Получить список пользователей с выбранными ids
     */
    Page<User> getUsersByIds(Set<Long> ids, Pageable pageable);
}
