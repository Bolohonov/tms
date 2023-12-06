package dev.bolohonov.tms.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    /**
     * уникальный идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * роль пользователя
     */
    String name;

    /**
     * Роли пользователя
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
