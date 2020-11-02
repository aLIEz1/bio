package com.example.bio.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.model.Biography;
import com.example.bio.service.BiographyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "bio", tags = "传记模块")
@RestController
@RequestMapping("/api/bio")
public class BiographyController extends BaseController {
    @Autowired
    private BiographyService biographyService;


    @ApiOperation(value = "新增传记")
    @PostMapping("/add")
    public Result<?> addBiography(@RequestBody @Valid BiographyDto biographyDto) {
        biographyService.saveBiography(biographyDto);
        return ok("发布成功！");
    }

    /**
     * 用户删除，软删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除传记", notes = "软删除")
    @PostMapping("/remove")
    public Result<?> removeBiography(@RequestParam(value = "id") @Valid String id) {
        UpdateWrapper<Biography> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id)
                .set("is_deleted", 1);
        biographyService.update(wrapper);
        return ok("删除成功");
    }

    @ApiOperation(value = "更新传记")
    @PostMapping("/update")
    public Result<?> updateBiography(@RequestBody @Valid BiographyDto biographyDto) {
        biographyService.saveBiography(biographyDto);
        return ok("修改成功");
    }

    /**
     * 通过SecurityContextHolder获取到当前登录用户的id查看私人传记
     *
     * @param pageQueryParams
     * @return
     */
    @ApiOperation(value = "分页获取私人传记",
            notes = "conditions中可传入的数据有 privacyLevel 0 表示公开，1表示私密,status 0 表示未发布 1表示已发布,categoryName 类别名称 " +
                    " orderBy 根据什么排序,可选属性(title,views,gmt_create,gmt_modified) 字符串传递,用英文逗号隔开，默认根据创建时间降序" +
                    " isAsc 是否升序，传入 true 或者 false 不要加引号！！！")
    @PostMapping("/myBiographiesPage")
    public Result<?> getBiographiesPage(@RequestBody PageQueryParams pageQueryParams) {
        return ok(biographyService.getBiographiesPage(pageQueryParams));
    }

    @ApiOperation(value = "分页获取他人传记",
            notes = "conditions中可传入的数据有ownerId,表示其他用户的id, categoryName 类别名称" +
                    " orderBy 根据什么排序,可选属性(title,views,gmt_create,gmt_modified) 字符串传递,用英文逗号隔开，默认根据创建时间降序" +
                    " isAsc 是否升序，传入 true 或者 false 不要加引号！！！")
    @PostMapping("/othersBiographiesPage")
    public Result<?> getOthersBiographies(@RequestBody PageQueryParams pageQueryParams) {
        return ok(biographyService.getOthersBiographies(pageQueryParams));
    }

}
