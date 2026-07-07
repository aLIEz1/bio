package com.example.bio.biography.application.executor.query;

import com.alibaba.cola.dto.PageResponse;
import com.example.bio.biography.application.query.BiographyPageQuery;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.biography.domain.gateway.BioTagGateway;
import com.example.bio.biography.domain.gateway.PageResult;
import com.example.bio.biography.domain.model.Biography;
import com.example.bio.biography.domain.model.BioTag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class BiographyPageQryExe {

    private final BiographyGateway biographyGateway;
    private final BioTagGateway tagGateway;

    public BiographyPageQryExe(BiographyGateway biographyGateway,
                               BioTagGateway tagGateway) {
        this.biographyGateway = biographyGateway;
        this.tagGateway = tagGateway;
    }

    public PageResponse<BiographyVO> execute(BiographyPageQuery query) {
        Map<String, Object> conditions = buildConditions(query);
        PageResult<Biography> page;

        if (query.getOwnerId() != null && query.getCurrentUserId() != null) {
            page = biographyGateway.findByOwnerId(query.getOwnerId(), conditions, query.getCurrent(), query.getSize());
        } else {
            page = biographyGateway.findPublicBiographies(conditions, query.getCurrent(), query.getSize());
        }

        List<BiographyVO> vos = toVoList(page.getRecords());
        PageResponse<BiographyVO> response = PageResponse.buildSuccess();
        response.setData(vos);
        response.setTotalCount((int) page.getTotal());
        response.setPageSize((int) page.getSize());
        response.setPageIndex((int) page.getCurrent());
        return response;
    }

    private Map<String, Object> buildConditions(BiographyPageQuery query) {
        Map<String, Object> conditions = new HashMap<>();
        if (query.getCategoryName() != null) conditions.put("categoryName", query.getCategoryName());
        if (query.getPrivacyLevel() != null) conditions.put("privacyLevel", query.getPrivacyLevel());
        if (query.getStatus() != null) conditions.put("status", query.getStatus());
        return conditions;
    }

    private List<BiographyVO> toVoList(List<Biography> records) {
        if (records.isEmpty()) return Collections.emptyList();

        List<String> bioIds = new ArrayList<>();
        for (Biography b : records) bioIds.add(b.getId());
        Map<String, Set<BioTag>> tagsMap = tagGateway.findByBiographyIds(bioIds);

        List<BiographyVO> vos = new ArrayList<>();
        for (Biography b : records) {
            BiographyVO vo = new BiographyVO(b);
            vo.setTags(tagsMap.getOrDefault(b.getId(), Collections.emptySet()));
            vos.add(vo);
        }
        return vos;
    }
}
