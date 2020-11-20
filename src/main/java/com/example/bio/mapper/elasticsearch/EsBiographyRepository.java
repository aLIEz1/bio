package com.example.bio.mapper.elasticsearch;

import com.example.bio.model.elasticsearch.EsBiography;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zhangfuqi
 * @date 2020/11/19
 */
public interface EsBiographyRepository extends ElasticsearchRepository<EsBiography, String> {
    /**
     * 模糊查询
     *
     * @param title        标题
     * @param content      内容
     * @param penName      笔名
     * @param categoryName 类别名
     * @param page         分页信息
     * @return 分页后的信息
     */
    Page<EsBiography> findByTitleLikeOrContentContainsOrPenNameContainsOrCategoryIdContains(String title, String content, String penName, String categoryName, Pageable page);
}
