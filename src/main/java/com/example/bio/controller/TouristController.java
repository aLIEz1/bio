package com.example.bio.controller;

import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.model.Biography;
import com.example.bio.service.BiographyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangfuqi
 * @date 2020/10/28
 */
@RestController
@RequestMapping("/api/public")
public class TouristController extends BaseController {
    @Autowired
    private BiographyService biographyService;

    @ApiOperation(value = "分页获取公开传记列表")
    @PostMapping("/bio")
    public Result<?> getPublicBiographyList(@RequestBody PageQueryParams pageQueryParams) {
        return ok(biographyService.getPublicBiographyList(pageQueryParams));
    }

}
