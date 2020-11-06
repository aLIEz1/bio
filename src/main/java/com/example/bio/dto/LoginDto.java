package com.example.bio.dto;

import com.example.bio.util.RegUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author super
 */
public class LoginDto {
    @NotBlank
    @Pattern(regexp = RegUtil.REG_USERNAME, message = "登录账号不能包含特殊字符且长度不能>16")
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
