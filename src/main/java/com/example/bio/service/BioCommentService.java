package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.CommentDto;
import com.example.bio.model.BioComment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface BioCommentService extends IService<BioComment> {
    /**
     * 获取评论列表
     *
     * @param pageQueryParams
     * @return List
     */
    List<BioComment> getCommentsPage(PageQueryParams pageQueryParams);

    /**
     * 提交评论
     *
     * @param commentDto
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    void commitComment(CommentDto commentDto);

    /**
     * 删评
     *
     * @param id
     */
    void deleteCommentById(String id);

    /**
     * 获取待审核评论列表（管理员）
     *
     * @param pageQueryParams 分页参数
     * @return 待审核评论列表
     */
    List<BioComment> getPendingComments(PageQueryParams pageQueryParams);

    /**
     * 审核通过评论（管理员）
     *
     * @param id 评论id
     */
    void approveComment(String id);

    /**
     * 批量审核通过评论（管理员）
     *
     * @param ids 评论id列表（逗号分隔）
     */
    void approveCommentBatch(List<String> ids);

}
