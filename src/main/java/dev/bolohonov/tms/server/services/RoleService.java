package dev.bolohonov.tms.server.services;

import dev.bolohonov.tms.server.model.Role;
import dev.bolohonov.tms.server.model.User;

import java.util.Collection;

public interface RoleService {

    Collection<Role> getRolesForUser(User user);
}
