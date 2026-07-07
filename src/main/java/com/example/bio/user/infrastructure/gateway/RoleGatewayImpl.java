package com.example.bio.user.infrastructure.gateway;

import com.example.bio.model.ERole;
import com.example.bio.model.Role;
import com.example.bio.service.RoleService;
import com.example.bio.user.domain.gateway.RoleGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleGatewayImpl implements RoleGateway {

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role getByRoleName(ERole eRole) {
        return roleService.getByRoleName(eRole);
    }

    @Override
    public void addRole(String userId, Set<Role> roles) {
        roleService.addRole(userId, roles);
    }

    @Override
    public Set<Role> getRoleByUserId(String userId) {
        return roleService.getRoleByUserId(userId);
    }

    @Override
    public void deleteRole(String userId, Set<Role> roles) {
        roleService.deleteRole(userId, roles);
    }
}
