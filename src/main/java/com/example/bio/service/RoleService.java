package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.model.ERole;
import com.example.bio.model.Role;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据role name获取role
     *
     * @param eRole
     * @return
     */
    Role getByRoleName(ERole eRole);

    /**
     * 向role user表中添加联系
     *
     * @param userId
     * @param roles
     * @return
     */
    void addRole(String userId, Set<Role> roles);

    /**
     * 根据用户id获取role
     *
     * @param userId
     * @return
     */
    List<Role> getRoleListByUserId(String userId);

}
