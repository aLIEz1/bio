package com.example.bio.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangfuqi
 * @date 2020/10/29
 */
@Getter
@Setter
public class BioCategoryDto {
    @NotBlank
    private String categoryName;
}
