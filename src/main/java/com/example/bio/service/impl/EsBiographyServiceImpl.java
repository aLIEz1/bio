package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bio.biography.infrastructure.persistence.mapper.BiographyMapper;
import com.example.bio.biography.infrastructure.persistence.po.BiographyPO;
import com.example.bio.mapper.elasticsearch.EsBiographyRepository;
import com.example.bio.model.elasticsearch.EsBiography;
import com.example.bio.service.EsBiographyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsBiographyServiceImpl implements EsBiographyService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EsBiographyServiceImpl.class);


    @Autowired
    private BiographyMapper biographyMapper;

    @Autowired
    private EsBiographyRepository esBiographyRepository;

    @Override
    public Iterable<EsBiography> getAll() {
        return esBiographyRepository.findAll();
    }

    @Override
    public int importAll() {
        QueryWrapper<BiographyPO> wrapper = new QueryWrapper<BiographyPO>()
                .eq("is_deleted", 0)
                .eq("privacy_level", 0)
                .eq("status", 1);

        List<BiographyPO> list = biographyMapper.selectList(wrapper);
        List<EsBiography> esBiographies = new ArrayList<>();
        for (BiographyPO po : list) {
            EsBiography es = new EsBiography();
            BeanUtils.copyProperties(po, es);
            esBiographies.add(es);
        }
        esBiographyRepository.saveAll(esBiographies);
        return esBiographies.size();
    }

    @Override
    public void delete(String id) {
        esBiographyRepository.deleteById(id);
    }

    @Override
    public EsBiography createdById(String id) {
        BiographyPO po = biographyMapper.selectById(id);
        if (po == null) {
            log.warn("Biography not found for id: {}", id);
            return null;
        }
        EsBiography es = new EsBiography();
        BeanUtils.copyProperties(po, es);
        esBiographyRepository.save(es);
        return es;
    }

    @Override
    public void deleteBatches(List<String> idList) {
        for (String s : idList) {
            esBiographyRepository.deleteById(s);
        }
    }

    @Override
    public Page<EsBiography> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esBiographyRepository.findByTitleLikeOrContentContainsOrPenNameContainsOrCategoryIdContains(
                keyword, keyword, keyword, keyword, pageable);
    }
}
