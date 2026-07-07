package com.example.bio.biography.interfaces.rest;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.command.*;
import com.example.bio.biography.application.executor.query.BiographyVO;
import com.example.bio.biography.application.query.BiographyByIdQuery;
import com.example.bio.biography.application.query.BiographyPageQuery;
import com.example.bio.biography.application.service.BiographyApplicationService;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.dto.UpdateBiographyDto;
import com.example.bio.security.service.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Api(value = "bio", tags = "传记模块")
@RestController
@RequestMapping("/api/bio")
public class BiographyController extends BaseController {

    private final BiographyApplicationService biographyAppService;

    public BiographyController(BiographyApplicationService biographyAppService) {
        this.biographyAppService = biographyAppService;
    }

    // ---------------------------------------------------------------- write

    @ApiOperation(value = "新增传记")
    @PostMapping("/add")
    public Result<?> addBiography(@RequestBody @Valid BiographyDto dto) {
        CreateBiographyCmd cmd = new CreateBiographyCmd();
        cmd.setCurrentUserId(currentUserId());
        cmd.setTitle(dto.getTitle());
        cmd.setContent(dto.getContent());
        cmd.setCategoryId(dto.getCategoryId());
        cmd.setPenName(dto.getPenName());
        cmd.setPrivacyLevel(dto.getPrivacyLevel());
        cmd.setStatus(dto.getStatus());
        cmd.setEnableComment(dto.getEnableComment());
        cmd.setNote(dto.getNote());
        cmd.setTagIds(extractTagIds(dto.getTags()));
        biographyAppService.create(cmd);
        return ok("发布成功！");
    }

    @ApiOperation(value = "删除传记", notes = "软删除，仅传记拥有者可删除")
    @PostMapping("/remove")
    public Result<?> removeBiography(@RequestParam("id") String id) {
        DeleteBiographyCmd cmd = new DeleteBiographyCmd();
        cmd.setCurrentUserId(currentUserId());
        cmd.setBioId(id);
        biographyAppService.delete(cmd);
        return ok("删除成功");
    }

    @ApiOperation(value = "更新传记")
    @PostMapping("/update")
    public Result<?> updateBiography(@RequestBody @Valid UpdateBiographyDto dto) {
        UpdateBiographyCmd cmd = new UpdateBiographyCmd();
        cmd.setCurrentUserId(currentUserId());
        cmd.setId(dto.getId());
        cmd.setTitle(dto.getTitle());
        cmd.setContent(dto.getContent());
        cmd.setCategoryId(dto.getCategoryId());
        cmd.setPenName(dto.getPenName());
        cmd.setPrivacyLevel(dto.getPrivacyLevel());
        cmd.setStatus(dto.getStatus());
        cmd.setEnableComment(dto.getEnableComment());
        cmd.setNote(dto.getNote());
        cmd.setTagIds(extractTagIds(dto.getTags()));
        biographyAppService.update(cmd);
        return ok("修改成功");
    }

    // ---------------------------------------------------------------- query

    @ApiOperation(value = "分页获取私人传记",
            notes = "conditions中可传入的数据有 privacyLevel / status / categoryName，默认根据创建时间降序")
    @PostMapping("/myBiographiesPage")
    public Result<?> getBiographiesPage(@RequestBody PageQueryParams params) {
        BiographyPageQuery query = new BiographyPageQuery();
        query.setCurrentUserId(currentUserId());
        query.setOwnerId(currentUserId());
        applyConditions(query, params);
        PageResponse<BiographyVO> resp = biographyAppService.getMyBiographies(query);
        return ok(toPageMap(resp));
    }

    @ApiOperation(value = "分页获取他人传记",
            notes = "conditions中可传入的数据有 ownerId / categoryName，默认根据创建时间降序")
    @PostMapping("/othersBiographiesPage")
    public Result<?> getOthersBiographies(@RequestBody PageQueryParams params) {
        BiographyPageQuery query = new BiographyPageQuery();
        applyConditions(query, params);
        PageResponse<BiographyVO> resp = biographyAppService.getPublicBiographies(query);
        return ok(toPageMap(resp));
    }

    @ApiOperation(value = "根据id获取私人传记")
    @GetMapping("/getById/{id}")
    public Result<?> getBiographyById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        BiographyByIdQuery query = new BiographyByIdQuery();
        query.setId(id);
        query.setCurrentUserId(currentUserId());
        SingleResponse<BiographyVO> resp = biographyAppService.getMyBiographyById(query);
        return ok(resp.getData());
    }

    @ApiOperation("根据id获取他人传记")
    @GetMapping("/getOthersById/{id}")
    public Result<?> getOthersBiographyById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        BiographyByIdQuery query = new BiographyByIdQuery();
        query.setId(id);
        SingleResponse<BiographyVO> resp = biographyAppService.getPublicBiographyById(query);
        return ok(resp.getData());
    }

    @ApiOperation(value = "点赞 / 取消点赞传记",
            notes = "已点赞则取消点赞，未点赞则点赞。返回 data=true 表示点赞成功，data=false 表示已取消点赞。")
    @PostMapping("/like/{id}")
    public Result<?> toggleLike(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        ToggleLikeCmd cmd = new ToggleLikeCmd();
        cmd.setCurrentUserId(currentUserId());
        cmd.setBioId(id);
        boolean liked = Boolean.TRUE.equals(biographyAppService.toggleLike(cmd).getData());
        return ok(liked ? "点赞成功" : "已取消点赞");
    }

    @ApiOperation(value = "检查当前用户是否已点赞某传记", notes = "返回 data=true 表示已点赞")
    @GetMapping("/hasLiked/{id}")
    public Result<?> hasLiked(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        return ok(biographyAppService.hasLiked(id, currentUserId()));
    }

    // ---------------------------------------------------------------- helpers

    private String currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
        return details.getId();
    }

    private Set<String> extractTagIds(Set<BiographyDto.TagRef> tags) {
        if (tags == null) return Collections.emptySet();
        return tags.stream()
                .filter(t -> t != null && t.getId() != null)
                .map(BiographyDto.TagRef::getId)
                .collect(Collectors.toSet());
    }

    private void applyConditions(BiographyPageQuery query, PageQueryParams params) {
        Map<String, Object> c = params.getConditions();
        if (c.containsKey("categoryName")) query.setCategoryName((String) c.get("categoryName"));
        if (c.containsKey("privacyLevel")) query.setPrivacyLevel((Integer) c.get("privacyLevel"));
        if (c.containsKey("status")) query.setStatus((Integer) c.get("status"));
        if (c.containsKey("ownerId")) query.setOwnerId((String) c.get("ownerId"));
        query.setCurrent(params.getPage().getCurrent());
        query.setSize(params.getPage().getSize());
    }

    private Map<String, Object> toPageMap(PageResponse<BiographyVO> resp) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("records", resp.getData());
        map.put("total", resp.getTotalCount());
        map.put("current", resp.getPageIndex());
        map.put("size", resp.getPageSize());
        long size = resp.getPageSize();
        long total = resp.getTotalCount();
        map.put("pages", size > 0 ? (long) Math.ceil((double) total / size) : 0);
        return map;
    }
}
