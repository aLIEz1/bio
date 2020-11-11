package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.model.BioTag;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface BioTagService extends IService<BioTag> {

    /**
     * 分页批量获取标签
     *
     * @param pageQueryParams
     * @return
     */
    List<BioTag> getTagsPage(PageQueryParams pageQueryParams);

    /**
     * 为博客添加标签
     *
     * @param bioId
     * @param tags
     */
    void addBiographyTags(String bioId, Set<BioTag> tags);

    /**
     * 根据bioId获取标签
     *
     * @param id
     * @return
     */
    List<BioTag> getTagsByBiographyId(String id);

}
