package com.example.bio.biography.interfaces.rest;

import cn.hutool.core.util.StrUtil;
import com.example.bio.biography.application.query.CategoryPageQuery;
import com.example.bio.biography.application.service.BioCategoryApplicationService;
import com.example.bio.biography.domain.model.BioCategory;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BioCategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "bio-category", tags = "传记类别模块")
@RestController
@RequestMapping("/api/bio-category")
public class BioCategoryController extends BaseController {

    private final BioCategoryApplicationService categoryAppService;

    public BioCategoryController(BioCategoryApplicationService categoryAppService) {
        this.categoryAppService = categoryAppService;
    }

    @ApiOperation(value = "新增传记类别")
    @PostMapping("/add")
    public Result<?> addCategory(@RequestBody @Valid BioCategoryDto categoryDto) {
        BioCategory cat = categoryAppService.create(categoryDto.getCategoryName()).getData();
        return ok(cat);
    }

    @ApiOperation(value = "分页获取类别信息", notes = "默认根据创建时间降序排序")
    @PostMapping("/getCategoriesPage")
    public Result<?> getAllCategoriesPage(@RequestBody PageQueryParams params) {
        CategoryPageQuery query = new CategoryPageQuery();
        query.setCurrent(params.getPage().getCurrent());
        query.setSize(params.getPage().getSize());
        return ok(categoryAppService.getPage(query).getData());
    }

    @ApiOperation(value = "删除类别信息，只有管理员能删除")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteCategoryById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        categoryAppService.delete(id);
        return ok("删除成功");
    }

    @ApiOperation(value = "修改类别信息，只有管理员能修改")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public Result<?> updateCategoryById(@PathVariable("id") String id,
                                        @RequestParam String categoryName) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        categoryAppService.updateName(id, categoryName);
        return ok("修改成功");
    }
}
