package com.example.bio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.mapper.RoleMapper;
import com.example.bio.model.ERole;
import com.example.bio.model.Role;
import com.example.bio.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public Role getByRoleName(ERole eRole) {
        return roleMapper.getByRoleName(eRole);
    }

    @Override
    public void addRole(String userId, Set<Role> roles) {
        roleMapper.addRole(userId, roles);
    }
}
