package com.example.bio.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.dto.SignupDto;
import com.example.bio.exception.Asserts;
import com.example.bio.mapper.UserMapper;
import com.example.bio.model.ERole;
import com.example.bio.model.Role;
import com.example.bio.model.User;
import com.example.bio.model.UserActiveToken;
import com.example.bio.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    private RoleService roleService;

    private UserActiveTokenService tokenService;

    private MailService mailService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setCacheService(UserCacheService cacheService) {
        this.cacheService = cacheService;
    }

    private UserCacheService cacheService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setTokenService(UserActiveTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public User getOneByUsername(String username) {
        User user = cacheService.getUser(username);
        if (user != null) {
            return user;
        } else {
            User oneByUsername = userMapper.getOneByUsername(username);
            cacheService.setUser(oneByUsername);
            return oneByUsername;
        }
//        return userMapper.getOneByUsername(username);
    }

    @Override
    public User getOneByEmail(String email) {
        return userMapper.getOneByEmail(email);
    }

    @Override
    @Transactional(rollbackFor = {Error.class, RuntimeException.class})
    public boolean unlockUser(String token) {
        UserActiveToken activeToken = tokenService.findByToken(token);
        if (activeToken != null && !activeToken.isExpired()) {
            userMapper.unlockUser(activeToken.getUser());
            tokenService.removeToken(activeToken.getId());
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional(rollbackFor = {Error.class, RuntimeException.class})
    public void registerUser(SignupDto signupDto) {
        if (!verifyAuthCode(signupDto.getAuthCode(), signupDto.getEmail())) {
            Asserts.fail("验证码错误");
        }
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Set<String> strRoles = signupDto.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.getByRoleName(ERole.ROLE_USER);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.getByRoleName(ERole.ROLE_ADMIN);
                        roles.add(adminRole);
                        break;
                    case "company":
                        Role companyRole = roleService.getByRoleName(ERole.ROLE_COMPANY);
                        roles.add(companyRole);
                        break;
                    default:
                        Role userRole = roleService.getByRoleName(ERole.ROLE_USER);
                        roles.add(userRole);
                }
            });
        }
        user.setInvitationCode(UUID.randomUUID().toString());
        //注册时设置锁定，需要验证邮箱方可解除锁定
        user.setIsLocked(1);
        save(user);
        user.setRoles(roles);
        roleService.addRole(user.getId(), roles);
        UserActiveToken token = new UserActiveToken();
        token.setExpiryDate(30);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        tokenService.addToken(token);
        mailService.sendActiveMail(user, token);
    }

    @Override
    public String generateAuthCode(String email) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        cacheService.setAuthCode(email, stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * 验证输入的验证码
     *
     * @param authCode
     * @param email
     * @return
     */
    private boolean verifyAuthCode(String authCode, String email) {
        if (StrUtil.isNotBlank(authCode)) {
            String realAuthCode = cacheService.getAuthCode(email);
            return authCode.equals(realAuthCode);
        } else {
            return false;
        }
    }
}
