package com.example.bio.dto;

import com.example.bio.util.RegUtil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author super
 */
public class SignupDto {
    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = RegUtil.REG_USERNAME,message = "登录账号不能包含特殊字符且长度不能>16")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = RegUtil.REG_EMAIL,message = "邮箱格式不正确")
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
