package com.example.bio.biography.domain.gateway;

import com.example.bio.biography.domain.model.BioCategory;

import java.util.List;
import java.util.Optional;

public interface BioCategoryGateway {

    void save(BioCategory category);

    Optional<BioCategory> findById(String id);

    void softDelete(String id);

    void updateName(String id, String categoryName);

    List<BioCategory> findPage(long current, long size);
}
