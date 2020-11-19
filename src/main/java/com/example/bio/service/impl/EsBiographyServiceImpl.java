package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bio.mapper.elasticsearch.EsBiographyRepository;
import com.example.bio.model.Biography;
import com.example.bio.model.elasticsearch.EsBiography;
import com.example.bio.service.BiographyService;
import com.example.bio.service.EsBiographyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangfuqi
 * @date 2020/11/19
 */
@Service
@Slf4j
public class EsBiographyServiceImpl implements EsBiographyService {

    @Autowired
    private BiographyService biographyService;

    @Autowired
    private EsBiographyRepository esBiographyRepository;

    @Override
    public int importAll() {
        QueryWrapper<Biography> wrapper = new QueryWrapper<>();
        wrapper
                .eq("is_deleted", 0)
                .eq("privacy_level", 0)
                .eq("status", 1);

        List<Biography> biographyList = biographyService.list(wrapper);
        List<EsBiography> esBiographies = new ArrayList<>();
        for (Biography biography : biographyList) {
            EsBiography esBiography = new EsBiography();
            BeanUtils.copyProperties(biography, esBiographies);
            esBiographies.add(esBiography);
        }
        return esBiographies.size();
    }

    @Override
    public void delete(String id) {
        esBiographyRepository.deleteById(id);
    }

    @Override
    public EsBiography createdById(String id) {
        return null;
    }

    @Override
    public void deleteBatches(List<String> idList) {
        for (String s : idList) {
            esBiographyRepository.deleteById(s);
        }
    }

    @Override
    public Page<EsBiography> search(String keyword, Integer pageNum, Integer pageSize) {
        Sort sort;
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esBiographyRepository.findByTitleOrContentOrPenNameOrCategoryName(keyword, keyword, keyword, keyword, pageable);
    }
}
