package com.example.bio.controller;

import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.service.BiographyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 游客访问前端控制器
 *
 * @author zhangfuqi
 * @date 2020/10/28
 */
@Api(value = "public",tags = "游客模块")
@RestController
@RequestMapping("/api/public")
public class TouristController extends BaseController {
    @Autowired
    private BiographyService biographyService;

    @ApiOperation(value = "分页获取公开传记列表", notes = "conditions中可传入的数据有ownerId,表示其他用户的id, categoryName 类别名称"+
            " orderBy 根据什么排序,可选属性(title,views,gmt_create,gmt_modified) 字符串传递,用英文逗号隔开，默认根据创建时间降序" +
            " isAsc 是否升序，传入 true 或者 false 不要加引号！！！")
    @PostMapping("/bio")
    public Result<?> getPublicBiographyList(@RequestBody PageQueryParams pageQueryParams) {
        return ok(biographyService.getPublicBiographyList(pageQueryParams));
    }

}
