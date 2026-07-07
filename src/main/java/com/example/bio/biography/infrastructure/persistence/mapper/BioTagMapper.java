package com.example.bio.biography.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bio.biography.infrastructure.persistence.po.BioTagPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BioTagMapper extends BaseMapper<BioTagPO> {

    void addBiographyTags(@Param("bioId") String bioId, @Param("tagIds") Set<String> tagIds);

    Set<BioTagPO> getTagsByBiographyId(@Param("id") String id);

    List<Map<String, Object>> getTagsByBiographyIds(@Param("bioIds") List<String> bioIds);
}
