package com.example.bio.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.dto.UpdateBiographyDto;
import com.example.bio.model.Biography;
import com.example.bio.service.BiographyService;
import com.example.bio.model.User;
import com.example.bio.service.UserService;
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

    private BiographyService biographyService;

    private UserService userService;

    @Autowired

    public void setBiographyService(BiographyService biographyService) {
        this.biographyService = biographyService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "新增传记")
    @PostMapping("/add")
    public Result<?> addBiography(@RequestBody @Valid BiographyDto biographyDto) {
        biographyService.saveBiography(biographyDto);
        return ok("发布成功！");
    }

    /**
     * 用户删除，软删除
     *
     * @param id 传记id
     * @return 删除成功或者失败信息
     */
    @ApiOperation(value = "删除传记", notes = "软删除，仅传记拥有者可删除")
    @PostMapping("/remove")
    public Result<?> removeBiography(@RequestParam(value = "id") String id) {
        Biography biography = biographyService.getById(id);
        if (biography == null) {
            return fail("传记不存在");
        }
        User currentUser = userService.getCurrentUser();
        if (currentUser == null || !biography.getOwnerId().equals(currentUser.getId())) {
            return fail("没有权限删除该传记");
        }
        UpdateWrapper<Biography> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id)
                .set("is_deleted", 1);
        biographyService.update(wrapper);
        return ok("删除成功");
    }

    @ApiOperation(value = "更新传记")
    @PostMapping("/update")
    public Result<?> updateBiography(@RequestBody @Valid UpdateBiographyDto biographyDto) {
        biographyService.updateBiography(biographyDto);
        return ok("修改成功");
    }

    /**
     * 通过SecurityContextHolder获取到当前登录用户的id查看私人传记
     *
     * @param pageQueryParams 分页查询信息
     * @return 分页后的传记列表
     */
    @ApiOperation(value = "分页获取私人传记",
            notes = "conditions中可传入的数据有 privacyLevel 0 表示公开，1表示私密,status 0 表示未发布 1表示已发布,categoryName 类别名称 " +
                    "默认根据创建时间降序排序")
    @PostMapping("/myBiographiesPage")
    public Result<?> getBiographiesPage(@RequestBody PageQueryParams pageQueryParams) {
        return ok(biographyService.getBiographiesPage(pageQueryParams));
    }

    @ApiOperation(value = "分页获取他人传记",
            notes = "conditions中可传入的数据有ownerId,表示其他用户的id, categoryName 类别名称，默认根据创建时间降序排序")
    @PostMapping("/othersBiographiesPage")
    public Result<?> getOthersBiographies(@RequestBody PageQueryParams pageQueryParams) {
        return ok(biographyService.getOthersBiographies(pageQueryParams));
    }

    @ApiOperation(value = "根据id获取私人传记")
    @GetMapping("/getById/{id}")
    public Result<?> getBiographyById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        return ok(biographyService.getBiographyById(id));
    }

    @ApiOperation("根据id获取他人传记")
    @GetMapping("/getOthersById/{id}")
    public Result<?> getOthersBiographyById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        return ok(biographyService.getOthersBiographyById(id));
    }

    @ApiOperation(value = "点赞 / 取消点赞传记",
            notes = "已点赞则取消点赞，未点赞则点赞。返回 data=true 表示点赞成功，data=false 表示已取消点赞。Redis Set 防重复。")
    @PostMapping("/like/{id}")
    public Result<?> toggleLike(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        boolean liked = biographyService.toggleLike(id);
        return ok(liked ? "点赞成功" : "已取消点赞");
    }

    @ApiOperation(value = "检查当前用户是否已点赞某传记", notes = "返回 data=true 表示已点赞")
    @GetMapping("/hasLiked/{id}")
    public Result<?> hasLiked(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        return ok(biographyService.hasLiked(id));
    }

}
