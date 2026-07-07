package com.example.bio.biography.domain.gateway;

import com.example.bio.biography.domain.model.BioComment;

import java.util.List;
import java.util.Optional;

public interface BioCommentGateway {

    void save(BioComment comment);

    Optional<BioComment> findById(String id);

    List<BioComment> findApprovedByBioId(String bioId);

    List<BioComment> findPending(long current, long size);

    void deleteByOwner(String commentId, String userId);

    void deleteByBioOwner(String commentId, String bioOwnerId);

    void softDelete(String id);

    void approve(String id);

    void approveBatch(List<String> ids);
}
