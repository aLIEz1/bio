package com.example.bio.biography.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.biography.application.command.CreateBiographyCmd;
import com.example.bio.biography.domain.gateway.BioCategoryGateway;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.biography.domain.gateway.BioTagGateway;
import com.example.bio.biography.domain.gateway.UserQueryGateway;
import com.example.bio.biography.domain.model.Biography;
import com.example.bio.biography.domain.model.BioCategory;
import com.example.bio.biography.domain.model.BiographyStatus;
import com.example.bio.biography.domain.model.PrivacyLevel;
import com.example.bio.exception.Asserts;
import com.example.bio.util.SnowFlakeUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Component
public class CreateBiographyCmdExe {

    private final BiographyGateway biographyGateway;
    private final BioTagGateway tagGateway;
    private final BioCategoryGateway categoryGateway;
    private final UserQueryGateway userQueryGateway;

    public CreateBiographyCmdExe(BiographyGateway biographyGateway,
                                 BioTagGateway tagGateway,
                                 BioCategoryGateway categoryGateway,
                                 UserQueryGateway userQueryGateway) {
        this.biographyGateway = biographyGateway;
        this.tagGateway = tagGateway;
        this.categoryGateway = categoryGateway;
        this.userQueryGateway = userQueryGateway;
    }

    @Transactional(rollbackFor = Exception.class)
    public Response execute(CreateBiographyCmd cmd) {
        BioCategory category = categoryGateway.findById(cmd.getCategoryId())
                .orElseThrow(() -> Asserts.build("分类不存在"));

        Biography bio = new Biography();
        bio.setId(SnowFlakeUtil.nextId().toString());
        bio.setOwnerId(cmd.getCurrentUserId());
        bio.setTitle(cmd.getTitle());
        bio.setContent(cmd.getContent());
        bio.setCategoryId(cmd.getCategoryId());
        bio.setCategoryName(category.getCategoryName());
        bio.setPenName(cmd.getPenName());
        bio.setPrivacyLevel(PrivacyLevel.of(cmd.getPrivacyLevel()));
        bio.setStatus(BiographyStatus.of(cmd.getStatus()));
        bio.setEnableComment(cmd.getEnableComment() == null ? 0 : cmd.getEnableComment());
        bio.setNote(cmd.getNote());
        bio.setLikes(0L);
        bio.setCommentNum(0L);

        Set<String> tagIds = cmd.getTagIds() == null ? Collections.emptySet() : cmd.getTagIds();
        bio.setTagIds(tagIds);

        biographyGateway.save(bio);
        if (!tagIds.isEmpty()) {
            tagGateway.bindTagsToBiography(bio.getId(), tagIds);
        }
        userQueryGateway.incrementBioNum(cmd.getCurrentUserId());

        return Response.buildSuccess();
    }
}
