package com.example.bio.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.EResult;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.model.BioTag;
import com.example.bio.service.BioTagService;
import com.example.bio.util.RegUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Api(value = "标签模块", tags = "标签模块")
@RestController
@RequestMapping("/api/bio-tag")
public class BioTagController extends BaseController {

    @Autowired
    private BioTagService tagService;

    @ApiOperation(value = "新增标签")
    @PostMapping("/add")
    public Result<?> addTag(@RequestParam String tagName) {
        if (RegUtil.username(tagName)) {
            BioTag tag = new BioTag();
            tag.setTagName(tagName);
            tagService.save(tag);
            return ok(EResult.SUCCESS.getCode(), "创建标签成功", tag);
        } else {
            return fail("请输入正确的tag标签名");
        }
    }

    @ApiOperation(value = "软删除标签")
    @DeleteMapping("/deleteById/{id}")
    public Result<?> deleteTag(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        } else {
            UpdateWrapper<BioTag> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", id)
                    .set("is_deleted", 1);
            tagService.remove(wrapper);
            return ok("删除成功");
        }

    }

    @ApiOperation(value = "根据id获取标签")
    @GetMapping("/getById/{id}")
    public Result<?> getTagById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        } else {
            QueryWrapper<BioTag> wrapper = new QueryWrapper<>();
            wrapper.eq("is_deleted", 0)
                    .eq("id", id);
            BioTag tag = tagService.getOne(wrapper);
            return ok(tag);
        }
    }

    @ApiOperation(value = "分页批量获取标签")
    @PostMapping("/getTagsPage")
    public Result<?> getTagsPage(@RequestBody PageQueryParams pageQueryParams) {
        return ok(tagService.getTagsPage(pageQueryParams));
    }

    @ApiOperation(value = "根据自传id获取标签")
    @GetMapping("/getTagsByBiographyId/{id}")
    public Result<?> getTagsByBiographyId(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        } else {
            return ok(tagService.getTagsByBiographyId(id));
        }
    }
}
