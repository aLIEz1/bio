package com.example.bio.biography.application.service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.executor.query.BioTagQryExe;
import com.example.bio.biography.application.query.TagPageQuery;
import com.example.bio.biography.domain.model.BioTag;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BioTagApplicationService {

    private final BioTagQryExe tagExe;

    public BioTagApplicationService(BioTagQryExe tagExe) {
        this.tagExe = tagExe;
    }

    public SingleResponse<BioTag> create(String tagName) {
        return tagExe.executeCreate(tagName);
    }

    public void delete(String id) {
        tagExe.executeDelete(id);
    }

    public SingleResponse<BioTag> getById(String id) {
        return tagExe.executeGetById(id);
    }

    public MultiResponse<BioTag> getPage(TagPageQuery query) {
        return tagExe.executePage(query);
    }

    public Set<BioTag> getTagsByBiographyId(String bioId) {
        return tagExe.executeGetByBiographyId(bioId);
    }
}
