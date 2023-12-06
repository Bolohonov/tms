package dev.bolohonov.tms.server.repo.role;

import dev.bolohonov.tms.server.model.Role;
import dev.bolohonov.tms.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Collection<Role> findAllByUser(User user);
}
