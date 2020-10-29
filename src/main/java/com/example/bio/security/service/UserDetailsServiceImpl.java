package com.example.bio.security.service;

import com.example.bio.model.Role;
import com.example.bio.model.User;
import com.example.bio.service.RoleService;
import com.example.bio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangfuqi
 * @date 2020/10/26
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    @Transactional(rollbackFor = UsernameNotFoundException.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOneByUsername(username);
        if (user != null) {
            List<Role> roleList = roleService.getRoleListByUserId(user.getId());
            List<GrantedAuthority> authorities = roleList.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                    .collect(Collectors.toList());
            return UserDetailsImpl.builder(user, authorities);
        } else {
            return null;
        }

    }
}
