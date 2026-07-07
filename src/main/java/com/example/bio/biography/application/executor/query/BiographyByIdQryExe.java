package com.example.bio.biography.application.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.query.BiographyByIdQuery;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.biography.domain.gateway.BioTagGateway;
import com.example.bio.biography.domain.model.Biography;
import com.example.bio.exception.Asserts;
import org.springframework.stereotype.Component;

@Component
public class BiographyByIdQryExe {

    private final BiographyGateway biographyGateway;
    private final BioTagGateway tagGateway;

    public BiographyByIdQryExe(BiographyGateway biographyGateway,
                               BioTagGateway tagGateway) {
        this.biographyGateway = biographyGateway;
        this.tagGateway = tagGateway;
    }

    public SingleResponse<BiographyVO> execute(BiographyByIdQuery query) {
        Biography bio = biographyGateway.findById(query.getId())
                .orElseThrow(() -> Asserts.build("传记不存在或无权访问"));

        if (query.getCurrentUserId() != null) {
            // private access: must be owner
            if (!bio.getOwnerId().equals(query.getCurrentUserId()) || bio.getDelFlag() != 0) {
                Asserts.fail("传记不存在");
            }
        } else {
            // public access
            if (!bio.isPublic()) {
                Asserts.fail("传记不存在或无权访问");
            }
        }

        BiographyVO vo = new BiographyVO(bio);
        vo.setTags(tagGateway.findByBiographyId(bio.getId()));
        return SingleResponse.of(vo);
    }
}
