package com.example.bio.biography.application.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.query.CategoryPageQuery;
import com.example.bio.biography.domain.gateway.BioCategoryGateway;
import com.example.bio.biography.domain.gateway.BioCategoryCache;
import com.example.bio.biography.domain.model.BioCategory;
import com.example.bio.exception.Asserts;
import com.example.bio.util.SnowFlakeUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BioCategoryQryExe {

    private final BioCategoryGateway categoryGateway;
    private final BioCategoryCache categoryCache;

    public BioCategoryQryExe(BioCategoryGateway categoryGateway,
                             BioCategoryCache categoryCache) {
        this.categoryGateway = categoryGateway;
        this.categoryCache = categoryCache;
    }

    public MultiResponse<BioCategory> executePage(CategoryPageQuery query) {
        List<BioCategory> categories = categoryGateway.findPage(query.getCurrent(), query.getSize());
        return MultiResponse.of(categories);
    }

    public SingleResponse<BioCategory> executeGetById(String id) {
        BioCategory cached = categoryCache.get(id);
        if (cached != null) return SingleResponse.of(cached);
        BioCategory cat = categoryGateway.findById(id)
                .orElseThrow(() -> Asserts.build("分类不存在"));
        categoryCache.put(cat);
        return SingleResponse.of(cat);
    }

    public SingleResponse<BioCategory> executeCreate(String categoryName) {
        BioCategory cat = new BioCategory();
        cat.setId(SnowFlakeUtil.nextId().toString());
        cat.setCategoryName(categoryName);
        categoryGateway.save(cat);
        return SingleResponse.of(cat);
    }

    public void executeDelete(String id) {
        categoryGateway.findById(id).orElseThrow(() -> Asserts.build("分类不存在"));
        categoryGateway.softDelete(id);
        categoryCache.evict(id);
    }

    public void executeUpdateName(String id, String categoryName) {
        categoryGateway.findById(id).orElseThrow(() -> Asserts.build("分类不存在"));
        categoryGateway.updateName(id, categoryName);
        categoryCache.evict(id);
    }
}
