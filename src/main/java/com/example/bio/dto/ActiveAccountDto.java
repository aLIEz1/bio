package com.example.bio.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangfuqi
 * @date 2020/10/25
 */
public class ActiveAccountDto {
    @NotBlank
    private String token;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
