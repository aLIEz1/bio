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

}
