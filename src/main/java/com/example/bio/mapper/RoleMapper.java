package com.example.bio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bio.model.ERole;
import com.example.bio.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface RoleMapper extends BaseMapper<Role> {
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
    void addRole(@Param("userId") String userId, @Param("roles") Set<Role> roles);

    /**
     * 根据用户id获取role
     *
     * @param userId
     * @return
     */
    List<Role> getRoleListByUserId(@Param("userId") String userId);

}
