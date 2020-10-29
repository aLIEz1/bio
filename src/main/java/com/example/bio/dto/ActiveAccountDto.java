package com.example.bio.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangfuqi
 * @date 2020/10/25
 */
@Getter
@Setter
public class ActiveAccountDto {
    @NotBlank
    private String token;
}
