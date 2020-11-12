package com.example.bio.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bio.common.annotation.RateLimiter;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.EResult;
import com.example.bio.common.api.Result;
import com.example.bio.common.lock.Callback;
import com.example.bio.common.lock.RedisLockTemplateImpl;
import com.example.bio.dto.LoginDto;
import com.example.bio.dto.SignupDto;
import com.example.bio.model.User;
import com.example.bio.security.service.UserDetailsImpl;
import com.example.bio.service.UserService;
import com.example.bio.util.CreateVerifyCode;
import com.example.bio.util.JwtUtils;
import com.example.bio.util.RegUtil;
import com.example.bio.util.ResponseUtil;
import com.example.bio.vo.JwtVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhangfuqi
 * @date 2020/10/26
 */
@RestController
@Api(value = "auth", tags = "认证模块")
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisLockTemplateImpl redisLockTemplate;


    @ApiOperation(value = "登录后返回token")
    @PostMapping("/signin")
    public Result<?> login(@RequestBody @Valid LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ok(new JwtVo(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @ApiOperation(value = "注册后发送激活邮件")
    @PostMapping("/signup")
    public Result<?> registerUser(@RequestBody @Valid SignupDto signupDto) {
        QueryWrapper<User> usernameWrapper = new QueryWrapper<>();
        usernameWrapper
                .eq("username", signupDto.getUsername())
                .eq("is_deleted", 0);
        if (userService.getOne(usernameWrapper) != null) {
            return fail("用户名已存在");
        }
        QueryWrapper<User> emailWrapper = new QueryWrapper<>();
        emailWrapper
                .eq("email", signupDto.getEmail())
                .eq("is_deleted", 0);
        if (userService.getOne(emailWrapper) != null) {
            return fail("邮箱已存在");
        }
        userService.registerUser(signupDto);
        return ok("注册成功");
    }

    @ApiOperation(value = "激活")
    @GetMapping("/active")
    public Result<?> activeUser(@RequestParam String token) {
        if (userService.unlockUser(token)) {
            return ok("账户激活成功");
        } else {
            return fail(EResult.FAILED);
        }
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping("/getAuthCode")
    @RateLimiter(rate = 1, rateInterval = 10000)
    public void getAuthCode(@RequestParam String email) throws IOException {
        redisLockTemplate.execute("RegisterGetAuthCode", 3, null, TimeUnit.SECONDS, new Callback() {
            @Override
            public Object onGetLock() throws InterruptedException, IOException {
                if (RegUtil.email(email)) {
                    String authCode = userService.generateAuthCode(email);
                    CreateVerifyCode createVerifyCode = new CreateVerifyCode(116, 36, 4, 10, authCode);
                    getResponse().setContentType("image/png");
                    createVerifyCode.write(getResponse().getOutputStream());
                } else {
                    ResponseUtil.out(getResponse(), ResponseUtil.resultMap(false, 500, "邮箱格式不正确，请重新输入"));
                }
                return null;

            }

            @Override
            public Object onTimeout() throws InterruptedException {
                return null;
            }
        });

    }

}
