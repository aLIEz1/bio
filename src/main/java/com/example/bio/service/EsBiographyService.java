package com.example.bio.service;

import com.example.bio.model.elasticsearch.EsBiography;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author zhangfuqi
 * @date 2020/11/19
 */
public interface EsBiographyService {
    /**
     * 从数据库中导入所有自传到ES
     *
     * @return int
     */
    int importAll();

    /**
     * 根据id删除自传
     *
     * @param id
     */
    void delete(String id);

    /**
     * 根据id创建自传
     *
     * @param id
     * @return
     */
    EsBiography createdById(String id);

    /**
     * 批量删除自传
     *
     * @param idList
     */
    void deleteBatches(List<String> idList);

    /**
     * 根据关键字搜索
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<EsBiography> search(String keyword, Integer pageNum, Integer pageSize);
}
