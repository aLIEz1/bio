package com.example.bio.controller;


import com.example.bio.common.api.Result;
import com.example.bio.common.base.BaseController;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BioCategoryDto;
import com.example.bio.model.BioCategory;
import com.example.bio.service.BioCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "bio-category", tags = "传记类别模块")
@RestController
@RequestMapping("/api/bio-category")
public class BioCategoryController extends BaseController {


    @Autowired
    private BioCategoryService categoryService;


    @ApiOperation(value = "新增传记类别")
    @PostMapping("/add")
    public Result<?> addCategory(@RequestBody @Valid BioCategoryDto categoryDto) {
        BioCategory category = new BioCategory();
        category.setCategoryName(categoryDto.getCategoryName());
        categoryService.save(category);
        return ok(category);
    }

    @ApiOperation(value = "分页获取类别信息",
            notes = " orderBy 根据什么排序,可选属性(title,views,gmt_create,gmt_modified) 字符串传递,用英文逗号隔开，默认根据创建时间降序" +
                    " isAsc 是否升序，传入 true 或者 false 不要加引号！！！")
    @PostMapping("getCategoriesPage")
    public Result<?> getAllCategoriesPage(@RequestBody PageQueryParams pageQueryParams) {
        return ok(categoryService.getAllCategoriesPage(pageQueryParams));
    }

}
