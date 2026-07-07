package com.example.bio.biography.application.service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.executor.query.BioCategoryQryExe;
import com.example.bio.biography.application.query.CategoryPageQuery;
import com.example.bio.biography.domain.model.BioCategory;
import org.springframework.stereotype.Service;

@Service
public class BioCategoryApplicationService {

    private final BioCategoryQryExe categoryExe;

    public BioCategoryApplicationService(BioCategoryQryExe categoryExe) {
        this.categoryExe = categoryExe;
    }

    public SingleResponse<BioCategory> create(String categoryName) {
        return categoryExe.executeCreate(categoryName);
    }

    public void delete(String id) {
        categoryExe.executeDelete(id);
    }

    public void updateName(String id, String categoryName) {
        categoryExe.executeUpdateName(id, categoryName);
    }

    public SingleResponse<BioCategory> getById(String id) {
        return categoryExe.executeGetById(id);
    }

    public MultiResponse<BioCategory> getPage(CategoryPageQuery query) {
        return categoryExe.executePage(query);
    }
}
