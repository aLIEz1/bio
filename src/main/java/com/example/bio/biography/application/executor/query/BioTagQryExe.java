package com.example.bio.biography.application.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.query.TagPageQuery;
import com.example.bio.biography.domain.gateway.BioTagGateway;
import com.example.bio.biography.domain.model.BioTag;
import com.example.bio.exception.Asserts;
import com.example.bio.util.SnowFlakeUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BioTagQryExe {

    private final BioTagGateway tagGateway;

    public BioTagQryExe(BioTagGateway tagGateway) {
        this.tagGateway = tagGateway;
    }

    public MultiResponse<BioTag> executePage(TagPageQuery query) {
        List<BioTag> tags = tagGateway.findPage(query.getCurrent(), query.getSize());
        return MultiResponse.of(tags);
    }

    public SingleResponse<BioTag> executeCreate(String tagName) {
        BioTag tag = new BioTag();
        tag.setId(SnowFlakeUtil.nextId().toString());
        tag.setTagName(tagName);
        tagGateway.save(tag);
        return SingleResponse.of(tag);
    }

    public SingleResponse<BioTag> executeGetById(String id) {
        BioTag tag = tagGateway.findById(id).orElseThrow(() -> Asserts.build("标签不存在"));
        return SingleResponse.of(tag);
    }

    public Set<BioTag> executeGetByBiographyId(String bioId) {
        return tagGateway.findByBiographyId(bioId);
    }

    public void executeDelete(String id) {
        tagGateway.findById(id).orElseThrow(() -> Asserts.build("标签不存在"));
        tagGateway.softDelete(id);
    }
}
