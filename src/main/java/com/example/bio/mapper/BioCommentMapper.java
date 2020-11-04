package com.example.bio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bio.model.BioComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface BioCommentMapper extends BaseMapper<BioComment> {

    /**
     * 根据BioId获取评论列表
     *
     * @param bioId
     * @return
     */
    List<BioComment> getCommentsByBioId(@Param("bioId") String bioId);

    /**
     * 查询子评论
     *
     * @param pid
     * @return
     */
    List<BioComment> selectChildren(@Param("pid") String pid);

    /**
     * 根据id删除评论
     *
     * @param id
     * @param userId
     */
    void deleteCommentById(@Param("id") String id, @Param("userId") String userId);

    /**
     * 自传拥有者删除评论
     *
     * @param id
     */
    void bioOwnerDeleteCommentById(@Param("id") String id, @Param("userId") String userId);


}
