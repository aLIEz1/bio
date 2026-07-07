package com.example.bio.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangfuqi
 * @date 2020/10/29
 */
public class BioCategoryDto {
    @NotBlank
    private String categoryName;

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
