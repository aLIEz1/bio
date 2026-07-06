package com.example.bio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bio.model.BioTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface BioTagMapper extends BaseMapper<BioTag> {
    /**
     * 新增博客标签记录
     *
     * @param bioId
     * @param tags
     */
    void addBiographyTags(@Param("bioId") String bioId, @Param("tags") Set<BioTag> tags);

    /**
     * 根据bioId获取标签
     *
     * @param id
     * @return
     */
    Set<BioTag> getTagsByBiographyId(@Param("id") String id);

    /**
     * 根据多个bioId批量获取标签，返回 bioId -> Set<BioTag> 的映射
     *
     * @param bioIds 传记ID列表
     * @return bioId与标签的映射列表
     */
    List<Map<String, Object>> getTagsByBiographyIds(@Param("bioIds") List<String> bioIds);
}
