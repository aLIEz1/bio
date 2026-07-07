package com.example.bio.biography.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bio.biography.infrastructure.persistence.po.BioCommentPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BioCommentMapper extends BaseMapper<BioCommentPO> {

    List<BioCommentPO> getCommentsByBioId(@Param("bioId") String bioId);

    void deleteCommentById(@Param("id") String id, @Param("userId") String userId);

    void bioOwnerDeleteCommentById(@Param("id") String id, @Param("userId") String userId);
}
