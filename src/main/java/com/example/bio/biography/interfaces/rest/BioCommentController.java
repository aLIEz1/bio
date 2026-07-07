package com.example.bio.biography.interfaces.rest;

import cn.hutool.core.util.StrUtil;
import com.example.bio.biography.application.command.ApproveCommentCmd;
import com.example.bio.biography.application.command.CommitCommentCmd;
import com.example.bio.biography.application.command.DeleteCommentCmd;
import com.example.bio.biography.application.query.CommentPageQuery;
import com.example.bio.biography.application.service.BioCommentApplicationService;
import com.example.bio.biography.domain.model.BioComment;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.CommentDto;
import com.example.bio.security.service.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(value = "bio-comment", tags = "评论模块")
@RestController
@RequestMapping("/api/bio-comment")
public class BioCommentController extends BaseController {

    private final BioCommentApplicationService commentAppService;

    public BioCommentController(BioCommentApplicationService commentAppService) {
        this.commentAppService = commentAppService;
    }

    @ApiOperation(value = "分页查询评论列表", notes = "bioId为必填项")
    @PostMapping("/getCommentsPage")
    public Result<List<BioComment>> getCommentsPage(@RequestBody PageQueryParams params) {
        CommentPageQuery query = new CommentPageQuery();
        query.setBioId((String) params.getConditions().get("bioId"));
        query.setCurrent(params.getPage().getCurrent());
        query.setSize(params.getPage().getSize());
        return ok(commentAppService.getCommentsPage(query).getData());
    }

    @ApiOperation(value = "提交评论")
    @PostMapping("/commitComment")
    public Result<?> commitComment(@RequestBody CommentDto dto) {
        CommitCommentCmd cmd = new CommitCommentCmd();
        cmd.setCurrentUserId(currentUserId());
        cmd.setBioId(dto.getBioId());
        cmd.setCommentBody(dto.getCommentBody());
        cmd.setParentId(dto.getParentId());
        commentAppService.commitComment(cmd);
        return ok("提交评论成功");
    }

    @ApiOperation(value = "删除评论", notes = "级联删除，删除父评论则子评论全部删除")
    @DeleteMapping("deleteCommentById/{id}")
    public Result<?> deleteCommentById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        DeleteCommentCmd cmd = new DeleteCommentCmd();
        cmd.setCurrentUserId(currentUserId());
        cmd.setCommentId(id);
        cmd.setRoleNames(currentUserRoles());
        commentAppService.deleteComment(cmd);
        return ok("删除成功");
    }

    @ApiOperation(value = "【管理员】获取待审核评论列表")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/pending")
    public Result<List<BioComment>> getPendingComments(@RequestBody PageQueryParams params) {
        return ok(commentAppService.getPendingComments(
                params.getPage().getCurrent(), params.getPage().getSize()).getData());
    }

    @ApiOperation(value = "【管理员】审核通过单条评论")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/{id}")
    public Result<?> approveComment(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) return fail("请输入正确的id");
        commentAppService.approveComment(id);
        return ok("审核通过");
    }

    @ApiOperation(value = "【管理员】批量审核通过评论", notes = "ids 为评论id，多个用英文逗号分隔")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approveBatch")
    public Result<?> approveCommentBatch(@RequestParam("ids") String ids) {
        if (StrUtil.isBlank(ids)) return fail("ids不能为空");
        ApproveCommentCmd cmd = new ApproveCommentCmd();
        cmd.setCommentIds(Arrays.asList(ids.split(",")));
        commentAppService.approveCommentBatch(cmd);
        return ok("批量审核通过");
    }

    // ---------------------------------------------------------------- helpers

    private String currentUserId() {
        UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return details.getId();
    }

    private Set<String> currentUserRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
