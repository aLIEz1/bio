package com.example.bio.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.CommentDto;
import com.example.bio.exception.Asserts;
import com.example.bio.mapper.BioCommentMapper;
import com.example.bio.model.BioComment;
import com.example.bio.model.Biography;
import com.example.bio.model.User;
import com.example.bio.service.BioCommentService;
import com.example.bio.service.BiographyService;
import com.example.bio.service.UserActiveService;
import com.example.bio.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Service
public class BioCommentServiceImpl extends ServiceImpl<BioCommentMapper, BioComment> implements BioCommentService {

    @Autowired
    private BiographyService biographyService;

    @Autowired
    private UserService userService;

    @Autowired
    private BioCommentMapper commentMapper;

    @Autowired
    private UserActiveService userActiveService;

    @Override
    public List<BioComment> getCommentsPage(PageQueryParams pageQueryParams) {
        Map<String, Object> conditions = pageQueryParams.getConditions();
        Object bioId = conditions.get("bioId");
        if (ObjectUtil.isNull(bioId)) {
            Asserts.fail("博客id不能为空");
        }
        List<BioComment> commentsByBioId = commentMapper.getCommentsByBioId((String) bioId);
        Page<BioComment> page = page(pageQueryParams.getPage());
        page.setRecords(commentsByBioId);
        return page.getRecords();
    }

    @Override
    public void commitComment(CommentDto commentDto) {
        BioComment comment = new BioComment();
        BeanUtils.copyProperties(commentDto, comment);
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        comment.setUserId(currentUser.getId());
        if (save(comment)) {
            addComments(comment.getBioId());
            // 更新用户活跃统计
            userActiveService.incrementCommentNum(currentUser.getId());
        }
    }

    @Override
    public void deleteCommentById(String id) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        BioComment comment = getById(id);
        if (comment == null) {
            Asserts.fail("评论不存在");
        }

        boolean isCommentOwner = comment.getUserId().equals(currentUser.getId());
        Biography biography = biographyService.getById(comment.getBioId());
        boolean isBioOwner = biography != null && biography.getOwnerId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles() != null && currentUser.getRoles().stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.getRoleName()));

        if (isCommentOwner) {
            commentMapper.deleteCommentById(id, currentUser.getId());
        } else if (isBioOwner) {
            commentMapper.bioOwnerDeleteCommentById(id, currentUser.getId());
        } else if (isAdmin) {
            // 管理员可以删除任何评论
            removeById(id);
        } else {
            Asserts.fail("没有权限删除该评论");
        }
    }

    @Override
    public List<BioComment> getPendingComments(PageQueryParams pageQueryParams) {
        QueryWrapper<BioComment> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_status", 0).eq("is_deleted", 0);
        IPage<BioComment> iPage = page(
                pageQueryParams.getPage().addOrder(OrderItem.desc("gmt_create")),
                wrapper
        );
        return iPage.getRecords();
    }

    @Override
    public void approveComment(String id) {
        UpdateWrapper<BioComment> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).eq("is_deleted", 0).set("comment_status", 1);
        update(wrapper);
    }

    @Override
    public void approveCommentBatch(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            Asserts.fail("评论id列表不能为空");
        }
        UpdateWrapper<BioComment> wrapper = new UpdateWrapper<>();
        wrapper.in("id", ids).eq("is_deleted", 0).set("comment_status", 1);
        update(wrapper);
    }

    private void addComments(String bioId) {
        Biography biography = biographyService.getById(bioId);
        if (biography == null) {
            Asserts.fail("传记不存在");
        }
        if (biography.getEnableComment() != 0) {
            Asserts.fail("该自传不允许评论！");
        } else {
            biography.setCommentNum(biography.getCommentNum() + 1);
            biographyService.updateById(biography);
        }
    }
}
