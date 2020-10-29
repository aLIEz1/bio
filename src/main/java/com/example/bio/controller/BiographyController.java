package com.example.bio.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.dto.BiographyDto;
import com.example.bio.model.Biography;
import com.example.bio.service.BioCategoryService;
import com.example.bio.service.BiographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@RestController
@RequestMapping("/api/bio")
public class BiographyController extends BaseController {
    @Autowired
    private BiographyService biographyService;


    @PostMapping("/add")
    public Result<?> addBiography(@RequestBody @Valid BiographyDto biographyDto) {
        biographyService.saveBiography(biographyDto);
        return ok("发布成功！");
    }

    @PostMapping("/remove")
    public Result<?> removeBiography(@RequestParam Long id) {
        biographyService.removeById(id);
        return ok("删除成功");
    }

    @PostMapping("/update")
    public Result<?> updateBiography(@RequestBody @Valid BiographyDto biographyDto) {
        biographyService.saveBiography(biographyDto);
        return ok("修改成功");
    }

    @GetMapping("/private")
    public Result<?> getPrivateBiography(@RequestBody PageQueryParams pageQueryParams) {
        QueryWrapper<Biography> wrapper = new QueryWrapper<>();
        return null;
    }

}
