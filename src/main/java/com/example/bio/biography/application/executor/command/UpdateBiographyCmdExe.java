package com.example.bio.biography.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.biography.application.command.UpdateBiographyCmd;
import com.example.bio.biography.domain.gateway.BioCategoryGateway;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.biography.domain.model.Biography;
import com.example.bio.biography.domain.model.BioCategory;
import com.example.bio.biography.domain.model.BiographyStatus;
import com.example.bio.biography.domain.model.PrivacyLevel;
import com.example.bio.exception.Asserts;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateBiographyCmdExe {

    private final BiographyGateway biographyGateway;
    private final BioCategoryGateway categoryGateway;

    public UpdateBiographyCmdExe(BiographyGateway biographyGateway,
                                 BioCategoryGateway categoryGateway) {
        this.biographyGateway = biographyGateway;
        this.categoryGateway = categoryGateway;
    }

    @Transactional(rollbackFor = Exception.class)
    public Response execute(UpdateBiographyCmd cmd) {
        Biography bio = biographyGateway.findById(cmd.getId())
                .orElseThrow(() -> Asserts.build("传记不存在"));

        if (!bio.getOwnerId().equals(cmd.getCurrentUserId())) {
            Asserts.fail("没有权限访问");
        }

        if (cmd.getTitle() != null) bio.setTitle(cmd.getTitle());
        if (cmd.getContent() != null) bio.setContent(cmd.getContent());
        if (cmd.getPenName() != null) bio.setPenName(cmd.getPenName());
        if (cmd.getPrivacyLevel() != null) bio.setPrivacyLevel(PrivacyLevel.of(cmd.getPrivacyLevel()));
        if (cmd.getStatus() != null) bio.setStatus(BiographyStatus.of(cmd.getStatus()));
        if (cmd.getEnableComment() != null) bio.setEnableComment(cmd.getEnableComment());
        if (cmd.getNote() != null) bio.setNote(cmd.getNote());

        if (cmd.getCategoryId() != null) {
            BioCategory category = categoryGateway.findById(cmd.getCategoryId())
                    .orElseThrow(() -> Asserts.build("分类不存在"));
            bio.setCategoryId(cmd.getCategoryId());
            bio.setCategoryName(category.getCategoryName());
        }

        biographyGateway.update(bio);
        return Response.buildSuccess();
    }
}
