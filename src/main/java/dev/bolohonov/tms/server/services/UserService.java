package dev.bolohonov.tms.server.services;

import dev.bolohonov.tms.server.dto.UserDto;
import dev.bolohonov.tms.server.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    /**
     * Получить список всех пользователей
     */
    Collection<UserDto> findAll();


    /**
     * Получить список пользователей
     */
    Collection<UserDto> getUsers(Integer[] ids, Integer from, Integer size);

    /**
     * Добавить пользователя
     */
    UserDto saveUser(User user);

    /**
     * Получить пользователя по id
     */
    Optional<UserDto> getUserById(Long userId);

    /**
     * Получить domain пользователя по имени
     */
    Optional<User> getDomainUserByName(String userName);

    /**
     * Получить domain пользователя по id
     */
    Optional<User> getDomainUserById(Long id);

    /**
     * Получить пользователя по email
     */
    Optional<UserDto> getUserByName(String name);

    /**
     * Удалить пользователя
     */
    void deleteUser(Long userId);
}
