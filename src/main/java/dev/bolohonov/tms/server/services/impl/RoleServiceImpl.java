package dev.bolohonov.tms.server.services.impl;

import dev.bolohonov.tms.server.model.Role;
import dev.bolohonov.tms.server.model.User;
import dev.bolohonov.tms.server.repo.role.RoleRepository;
import dev.bolohonov.tms.server.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Role> getRolesForUser(User user) {
        return roleRepository.findAllByUser(user);
    }
}
