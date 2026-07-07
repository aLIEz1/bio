package com.example.bio.user.application.command;

import com.alibaba.cola.dto.Command;
import com.example.bio.model.ERole;
import com.example.bio.model.Role;

import java.util.Set;

public class UpdateUserRoleCmd extends Command {
    private String userId;
    private Set<String> roles;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
