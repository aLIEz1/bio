package com.example.bio.user.domain.gateway;

import com.example.bio.model.ERole;
import com.example.bio.model.Role;

import java.util.Set;

public interface RoleGateway {
    Role getByRoleName(ERole eRole);
    void addRole(String userId, Set<Role> roles);
    Set<Role> getRoleByUserId(String userId);
    void deleteRole(String userId, Set<Role> roles);
}
