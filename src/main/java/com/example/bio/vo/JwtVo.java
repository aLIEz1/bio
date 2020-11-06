package com.example.bio.vo;

import com.example.bio.common.constant.SecurityConstant;

import java.util.List;

/**
 * 前端展示视图
 *
 * @author zhangfuqi
 * @date 2020/10/26
 */
public class JwtVo {

    private String accessToken;
    private String tokenType = SecurityConstant.TOKEN_SPLIT;
    private String id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtVo(String accessToken, String id, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
