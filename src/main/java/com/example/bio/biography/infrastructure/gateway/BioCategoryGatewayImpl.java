package com.example.bio.biography.infrastructure.gateway;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.biography.domain.gateway.BioCategoryGateway;
import com.example.bio.biography.domain.model.BioCategory;
import com.example.bio.biography.infrastructure.persistence.mapper.BioCategoryMapper;
import com.example.bio.biography.infrastructure.persistence.po.BioCategoryPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BioCategoryGatewayImpl
        extends ServiceImpl<BioCategoryMapper, BioCategoryPO>
        implements BioCategoryGateway {

    @Override
    public void save(BioCategory cat) {
        save(toPO(cat));
    }

    @Override
    public Optional<BioCategory> findById(String id) {
        QueryWrapper<BioCategoryPO> wrapper = new QueryWrapper<BioCategoryPO>()
                .eq("id", id).eq("is_deleted", 0);
        return Optional.ofNullable(getOne(wrapper)).map(this::toDomain);
    }

    @Override
    public void softDelete(String id) {
        UpdateWrapper<BioCategoryPO> wrapper = new UpdateWrapper<BioCategoryPO>()
                .eq("id", id).set("is_deleted", 1);
        update(wrapper);
    }

    @Override
    public void updateName(String id, String categoryName) {
        UpdateWrapper<BioCategoryPO> wrapper = new UpdateWrapper<BioCategoryPO>()
                .eq("id", id).set("category_name", categoryName);
        update(wrapper);
    }

    @Override
    public List<BioCategory> findPage(long current, long size) {
        QueryWrapper<BioCategoryPO> wrapper = new QueryWrapper<BioCategoryPO>()
                .eq("is_deleted", 0);
        Page<BioCategoryPO> page = page(
                new Page<BioCategoryPO>(current, size).addOrder(OrderItem.desc("gmt_create")),
                wrapper);
        return page.getRecords().stream().map(this::toDomain).collect(Collectors.toList());
    }

    BioCategory toDomain(BioCategoryPO po) {
        BioCategory c = new BioCategory();
        c.setId(po.getId());
        c.setCategoryName(po.getCategoryName());
        c.setGmtCreate(po.getGmtCreate());
        c.setGmtModified(po.getGmtModified());
        c.setDelFlag(po.getDelFlag() == null ? 0 : po.getDelFlag());
        return c;
    }

    private BioCategoryPO toPO(BioCategory c) {
        BioCategoryPO po = new BioCategoryPO();
        po.setId(c.getId());
        po.setCategoryName(c.getCategoryName());
        po.setDelFlag(c.getDelFlag());
        return po;
    }
}
