package com.example.bio.controller;

import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.EResult;
import com.example.bio.common.api.Result;
import com.example.bio.dto.ActiveAccountDto;
import com.example.bio.dto.LoginDto;
import com.example.bio.dto.SignupDto;
import com.example.bio.util.JwtUtils;
import com.example.bio.security.service.UserDetailsImpl;
import com.example.bio.service.UserService;
import com.example.bio.vo.JwtVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangfuqi
 * @date 2020/10/26
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;


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
        if (userService.getOneByUsername(signupDto.getUsername()) != null) {
            return fail("Error: Username is already taken!");
        }
        if (userService.getOneByEmail(signupDto.getEmail()) != null) {
            return fail("Error: Email is already in use!");
        }
        userService.registerUser(signupDto);
        return ok("registered successfully !");
    }

    @ApiOperation(value = "激活")
    @PostMapping("/active")
    public Result<?> activeUser(@RequestBody @Valid ActiveAccountDto activeAccount) {
        if (userService.unlockUser(activeAccount.getToken())) {
            return ok("Your account has been activated");
        } else {
            return fail(EResult.FAILED);
        }
    }

}
