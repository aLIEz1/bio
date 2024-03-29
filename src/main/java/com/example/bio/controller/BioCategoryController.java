package com.example.bio.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BioCategoryDto;
import com.example.bio.model.BioCategory;
import com.example.bio.service.BioCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    private BioCategoryService categoryService;

    @Autowired
    public void setCategoryService(BioCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "新增传记类别")
    @PostMapping("/add")
    public Result<?> addCategory(@RequestBody @Valid BioCategoryDto categoryDto) {
        BioCategory category = new BioCategory();
        category.setCategoryName(categoryDto.getCategoryName());
        categoryService.save(category);
        return ok(category);
    }

    @ApiOperation(value = "分页获取类别信息",
            notes = "默认根据创建时间降序排序")
    @PostMapping("/getCategoriesPage")
    public Result<?> getAllCategoriesPage(@RequestBody PageQueryParams pageQueryParams) {
        return ok(categoryService.getAllCategoriesPage(pageQueryParams));
    }

    @ApiOperation(value = "删除类别信息，只有管理员能删除")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteCategoryById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        categoryService.removeById(id);
        return ok("删除成功");
    }

    @PutMapping("/update/{id}")
    public Result<?> updateCategoryById(@PathVariable("id") String id,
                                        @RequestParam String categoryName) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        UpdateWrapper<BioCategory> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("id", id)
                .set("category_name", categoryName);
        categoryService.update(updateWrapper);
        return ok("修改成功");
    }
}
