package com.example.bio.biography.domain.gateway;

import com.example.bio.biography.domain.model.Biography;

import java.util.Map;
import java.util.Optional;

public interface BiographyGateway {

    void save(Biography biography);

    void update(Biography biography);

    Optional<Biography> findById(String id);

    void softDelete(String id);

    void updateLikes(String id, boolean increment);

    void incrementCommentNum(String id);

    PageResult<Biography> findByOwnerId(String ownerId, Map<String, Object> conditions,
                                        long current, long size);

    PageResult<Biography> findPublicBiographies(Map<String, Object> conditions,
                                                long current, long size);
}
