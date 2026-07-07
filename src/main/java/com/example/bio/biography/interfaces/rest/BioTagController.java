package com.example.bio.biography.interfaces.rest;

import cn.hutool.core.util.StrUtil;
import com.example.bio.biography.application.query.TagPageQuery;
import com.example.bio.biography.application.service.BioTagApplicationService;
import com.example.bio.biography.domain.model.BioTag;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.EResult;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.util.RegUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(value = "标签模块", tags = "标签模块")
@RestController
@RequestMapping("/api/bio-tag")
public class BioTagController extends BaseController {

    private final BioTagApplicationService tagAppService;

    public BioTagController(BioTagApplicationService tagAppService) {
        this.tagAppService = tagAppService;
    }

    @ApiOperation(value = "【管理员】新增标签")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public Result<?> addTag(@RequestParam String tagName) {
        if (!RegUtil.username(tagName)) return fail("请输入正确的tag标签名");
        BioTag tag = tagAppService.create(tagName).getData();
        return ok(EResult.SUCCESS.getCode(), "创建标签成功", tag);
    }

    @ApiOperation(value = "【管理员】软删除标签")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public Result<?> deleteTag(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        tagAppService.delete(id);
        return ok("删除成功");
    }

    @ApiOperation(value = "根据id获取标签")
    @GetMapping("/getById/{id}")
    public Result<?> getTagById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        return ok(tagAppService.getById(id).getData());
    }

    @ApiOperation(value = "分页批量获取标签")
    @PostMapping("/getTagsPage")
    public Result<?> getTagsPage(@RequestBody PageQueryParams params) {
        TagPageQuery query = new TagPageQuery();
        query.setCurrent(params.getPage().getCurrent());
        query.setSize(params.getPage().getSize());
        return ok(tagAppService.getPage(query).getData());
    }

    @ApiOperation(value = "根据自传id获取标签")
    @GetMapping("/getTagsByBiographyId/{id}")
    public Result<?> getTagsByBiographyId(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        return ok(tagAppService.getTagsByBiographyId(id));
    }
}
