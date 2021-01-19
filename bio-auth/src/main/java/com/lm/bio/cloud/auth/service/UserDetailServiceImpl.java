package com.lm.bio.cloud.auth.service;

import cn.hutool.core.util.StrUtil;
import com.lm.bio.cloud.common.core.constant.AuthConstant;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangfuqi
 * @date 2020/12/31
 */
@Primary
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        String loginType = requestAttributes.getRequest().getParameter(AuthConstant.LOGIN_TYPE);
        if (StrUtil.isBlank(loginType)) {
            throw new AuthenticationServiceException("登录类型不能为null");
        }
        UserDetails userDetails;
        try {
            // refresh_token 进行纠正
            String grantType = requestAttributes.getRequest().getParameter("grant_type");
            if (AuthConstant.REFRESH_TYPE.equalsIgnoreCase(grantType)) {
                username = adjustUsername(username, loginType);
            }

            switch (loginType) {
                case AuthConstant.ADMIN_TYPE:
                    userDetails = loadSysUserByUsername(username);
                    break;
                case AuthConstant.MEMBER_TYPE:
                    userDetails = loadMemberUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持该登录方式" + loginType);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException("用户名" + username + "不存在");
        }
        return userDetails;
    }

    /**
     * 纠正用户的名称
     *
     * @param username  用户id
     * @param loginType admin_type or member_type
     * @return 更正过的名称
     */
    private String adjustUsername(String username, String loginType) {
        if (AuthConstant.ADMIN_TYPE.equals(loginType)) {
            // 管理员的纠正方式
            return jdbcTemplate.queryForObject(AuthConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        }
        if (AuthConstant.MEMBER_TYPE.equals(loginType)) {
            // 会员的纠正方式
            return jdbcTemplate.queryForObject(AuthConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        }
        return username;
    }

    /**
     * 管理员登录
     * loadSysUserByUsername
     *
     * @param username 用户名
     * @return UserDetails
     */
    private UserDetails loadSysUserByUsername(String username) {
        return jdbcTemplate.queryForObject(AuthConstant.QUERY_ADMIN_SQL, (rs, rowNum) -> {
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("用户名" + username + "不存在");
            }
            long id = rs.getLong("id"); // 用户的id
            String password = rs.getString("password"); // 用户的密码
            int status = rs.getInt("status");
            return new User(   // 3 封装成一个UserDetails对象，返回
                    String.valueOf(id), //使用id->username
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    getSysUserPermissions(id)
            );
        }, username);

    }

    /**
     * 会员登录
     *
     * @param username 用户名
     * @return UserDetails
     */
    private UserDetails loadMemberUserByUsername(String username) {
        return jdbcTemplate.queryForObject(AuthConstant.QUERY_MEMBER_SQL, (rs, rowNum) -> {
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("用户：" + username + "不存在");
            }
            long id = rs.getLong("id"); // 会员的id
            String password = rs.getString("password");// 会员的登录密码
            int status = rs.getInt("status"); // 会员的状态
            return new User(
                    String.valueOf(id),
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }, username, username);

    }

    /**
     * 通过用户的id 查询用户的权限
     *
     * @param id 用户id
     * @return 用户权限
     */
    private Collection<? extends GrantedAuthority> getSysUserPermissions(long id) {
        // 1 当用户为超级管理员时，他拥有所有的权限数据
        String roleCode = jdbcTemplate.queryForObject(AuthConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        // 权限的名称
        List<String> permissions;
        // 超级用户
        if (AuthConstant.ADMIN_ROLE_CODE.equals(roleCode)) {
            permissions = jdbcTemplate.queryForList(AuthConstant.QUERY_ALL_PERMISSIONS, String.class);
            // 2 普通用户，需要使用角色->权限数据
        } else {
            permissions = jdbcTemplate.queryForList(AuthConstant.QUERY_PERMISSION_SQL, String.class, id);
        }
        if (permissions.isEmpty()) {
            return Collections.emptySet();
        }
        return permissions.stream()
                .distinct() // 去重
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
