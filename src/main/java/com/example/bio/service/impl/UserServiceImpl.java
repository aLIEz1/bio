package com.example.bio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.common.component.MyMailSender;
import com.example.bio.dto.ResetPasswordDto;
import com.example.bio.dto.SignupDto;
import com.example.bio.exception.Asserts;
import com.example.bio.mapper.UserMapper;
import com.example.bio.model.*;
import com.example.bio.security.service.UserDetailsImpl;
import com.example.bio.service.RoleService;
import com.example.bio.service.UserActiveTokenService;
import com.example.bio.service.UserCacheService;
import com.example.bio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private RoleService roleService;

    private UserActiveTokenService tokenService;

    private PasswordEncoder passwordEncoder;

    private MyMailSender myMailSender;

    private UserCacheService cacheService;

    @Autowired
    public void setMailSender(MyMailSender mailSender) {
        this.myMailSender = mailSender;
    }

    @Autowired
    public void setCacheService(UserCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setTokenService(UserActiveTokenService tokenService) {
        this.tokenService = tokenService;
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
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username)
                    .eq("is_deleted", 0);
            User one = getOne(wrapper);
            one.setRoles(roleService.getRoleByUserId(one.getId()));
            cacheService.setUser(one);
            return one;
        }
    }

    @Override
    public boolean unlockUser(String token) {
        UserActiveToken activeToken = tokenService.findByToken(token);
        if (activeToken != null && !activeToken.isExpired()) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .eq("id", activeToken.getUser().getId())
                    .eq("is_deleted", 0)
                    .set("is_locked", 0);
            update(updateWrapper);
            tokenService.removeById(activeToken.getId());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void registerUser(SignupDto signupDto) {
        if (!verifyAuthCode(signupDto.getAuthCode(), signupDto.getEmail())) {
            Asserts.fail("验证码错误");
        }
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleService.getByRoleName(ERole.ROLE_USER);
        roles.add(userRole);
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
        myMailSender.sendMail(token);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails != null) {
            return getOneByUsername(userDetails.getUsername());
        } else {
            return null;
        }
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

    @Override
    public void getResetPasswordToken(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email)
                .eq("is_locked", 0)
                .eq("is_deleted", 0);
        User user = getOne(wrapper);
        if (user != null) {
            String token = IdUtil.fastSimpleUUID().substring(0, 5);
            cacheService.setResetPasswordToken(email, token);
            myMailSender.sendResetPasswordEmail(new PasswordResetToken(email, token));
        } else {
            Asserts.fail("该用户不存在！");
        }

    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        String token = cacheService.getResetPasswordToken(resetPasswordDto.getEmailAddress());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", resetPasswordDto.getEmailAddress())
                .eq("is_locked", 0)
                .eq("is_deleted", 0);
        User user = getOne(wrapper);
        if (user != null) {
            if (resetPasswordDto.getTokenSecret().equals(token)) {
                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper
                        .eq("id", user.getId())
                        .set("password", passwordEncoder.encode(resetPasswordDto.getPassword()));
                update(updateWrapper);
                cacheService.deleteUserCache(user.getUsername());
                cacheService.deleteResetPasswordToken(user.getEmail());
            } else {
                Asserts.fail("token错误或者过期");
            }
        } else {
            Asserts.fail("该用户不存在");
        }


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
