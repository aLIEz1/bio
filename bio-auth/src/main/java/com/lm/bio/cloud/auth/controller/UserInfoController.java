package com.lm.bio.cloud.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author zhangfuqi
 * @date 2020/12/30
 */
@RestController
public class UserInfoController {
    /**
     * 当前的登录用户对象
     * 使用ThreadLocal实现
     *
     * @param principal 用户信息
     * @return 用户信息
     */
    @GetMapping("/user/info")
    public Principal userInfo(Principal principal) {
        return principal;
    }
}
