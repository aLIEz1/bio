package com.example.bio.biography.domain.gateway;

import com.example.bio.biography.domain.model.BioTag;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface BioTagGateway {

    void save(BioTag tag);

    Optional<BioTag> findById(String id);

    void softDelete(String id);

    List<BioTag> findPage(long current, long size);

    void bindTagsToBiography(String bioId, Set<String> tagIds);

    Set<BioTag> findByBiographyId(String bioId);

    Map<String, Set<BioTag>> findByBiographyIds(List<String> bioIds);

    List<BioTag> findByIds(Set<String> ids);
}
