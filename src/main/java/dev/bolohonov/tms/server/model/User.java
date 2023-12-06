package dev.bolohonov.tms.server.model;

import lombok.*;

import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Класс с описанием пользователя - User
 */
@Entity
@Table(name = "users")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * Уникальный идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Логин (email) пользователя, должно быть уникальным
     */
    @NotBlank
    @Email
    private String name;
    /**
     * хэш-код пароля
     */
    private String password_hash;
}
