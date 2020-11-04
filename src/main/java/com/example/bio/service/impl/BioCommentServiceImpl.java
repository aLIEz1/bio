package com.example.bio.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
        }
    }

    @Override
    public void deleteCommentById(String id) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        BioComment comment = getById(id);
        if (comment.getUserId().equals(currentUser.getId())) {
            commentMapper.deleteCommentById(id, currentUser.getId());
        }

        Biography biography = biographyService.getById(comment.getBioId());
        if (biography.getOwnerId().equals(currentUser.getId())) {
            commentMapper.bioOwnerDeleteCommentById(id, currentUser.getId());
        }

    }

    private void addComments(String bioId) {
        Biography biography = biographyService.getById(bioId);
        if (biography.getEnableComment() != 0) {
            Asserts.fail("该自传不允许评论！");
        } else {
            biography.setCommentNum(biography.getCommentNum() + 1);
            biographyService.updateById(biography);
        }

    }
}
