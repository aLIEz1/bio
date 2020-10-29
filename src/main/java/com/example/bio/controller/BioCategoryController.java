package com.example.bio.controller;


import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.dto.BioCategoryDto;
import com.example.bio.model.BioCategory;
import com.example.bio.service.BioCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@RestController
@RequestMapping("/api/bio-category")
public class BioCategoryController extends BaseController {

    @Autowired
    private BioCategoryService categoryService;

    @PostMapping("/add")
    public Result<?> addCategory(@RequestBody @Valid BioCategoryDto categoryDto) {
        BioCategory category =new BioCategory();
        category.setCategoryName(categoryDto.getCategoryName());
        categoryService.save(category);
        return ok(category);
    }

}
