package com.example.bio.controller;


import cn.hutool.core.util.StrUtil;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.CommentDto;
import com.example.bio.model.BioComment;
import com.example.bio.service.BioCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Api(value = "bio-comment", tags = "评论模块")
@RestController
@RequestMapping("/api/bio-comment")
public class BioCommentController extends BaseController {

    private BioCommentService commentService;

    @Autowired
    public void setCommentService(BioCommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "分页查询评论列表", notes = "bioId为必填项")
    @PostMapping("/getCommentsPage")
    public Result<List<BioComment>> getCommentsPage(@RequestBody PageQueryParams pageQueryParams) {
        return ok(commentService.getCommentsPage(pageQueryParams));
    }

    @ApiOperation(value = "提交评论")
    @PostMapping("/commitComment")
    public Result<?> commitComment(@RequestBody CommentDto commentDto) {
        commentService.commitComment(commentDto);
        return ok("提交评论成功");
    }

    @ApiOperation(value = "删除评论", notes = "级联删除，删除父评论则子评论全部删除,自传拥有者，评论拥有者可以删除")
    @DeleteMapping("deleteCommentById/{id}")
    public Result<?> deleteCommentById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        commentService.deleteCommentById(id);
        return ok("删除成功");
    }

    @ApiOperation(value = "【管理员】获取待审核评论列表")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/pending")
    public Result<List<BioComment>> getPendingComments(@RequestBody PageQueryParams pageQueryParams) {
        return ok(commentService.getPendingComments(pageQueryParams));
    }

    @ApiOperation(value = "【管理员】审核通过单条评论")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/{id}")
    public Result<?> approveComment(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        commentService.approveComment(id);
        return ok("审核通过");
    }

    @ApiOperation(value = "【管理员】批量审核通过评论", notes = "ids 为评论id，多个用英文逗号分隔")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approveBatch")
    public Result<?> approveCommentBatch(@RequestParam("ids") String ids) {
        if (StrUtil.isBlank(ids)) {
            return fail("ids不能为空");
        }
        commentService.approveCommentBatch(Arrays.asList(ids.split(",")));
        return ok("批量审核通过");
    }

}
