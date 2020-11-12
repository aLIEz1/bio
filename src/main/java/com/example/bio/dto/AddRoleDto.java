package com.example.bio.dto;

import com.example.bio.model.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author zhangfuqi
 * @date 2020/11/12
 */
public class AddRoleDto {
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @NotNull(message = "用户列表不能为空")
    private Set<String> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
