package com.example.bio.controller;

import com.example.bio.common.annotation.RateLimiter;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.common.lock.Callback;
import com.example.bio.common.lock.RedisLockTemplateImpl;
import com.example.bio.service.BiographyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * 游客访问前端控制器
 *
 * @author zhangfuqi
 * @date 2020/10/28
 */
@Api(value = "public", tags = "游客模块")
@RestController
@RequestMapping("/api/public")
public class TouristController extends BaseController {
    @Autowired
    private BiographyService biographyService;
    @Autowired
    private RedisLockTemplateImpl redisLockTemplate;

    @ApiOperation(value = "分页获取公开传记列表", notes = "conditions中可传入的数据有ownerId,表示其他用户的id, categoryName 类别名称" +
            "默认根据创建时间降序排序")
    @RateLimiter(rate = 1, rateInterval = 5000)
    @PostMapping("/getBiographiesPage")
    public Result<?> getPublicBiographyList(@RequestBody PageQueryParams pageQueryParams) {
        Object execute = redisLockTemplate.execute("touristGetBiographiesPage", 3, null, TimeUnit.SECONDS, new Callback() {
            @Override
            public Object onGetLock() throws InterruptedException {
                return biographyService.getPublicBiographyList(pageQueryParams);
            }

            @Override
            public Object onTimeout() throws InterruptedException {
                return null;
            }
        });

        return ok(execute);
    }

    @ApiOperation("获取传记详情")
    @RateLimiter(rate = 1, rateInterval = 5000)
    @GetMapping("/getBiographyById/{id}")
    public Result<Object> getBiographyById(@PathVariable("id") String id) {
        Object execute = redisLockTemplate.execute(id, 3, null, TimeUnit.SECONDS, new Callback() {
            @Override
            public Object onGetLock() throws InterruptedException {
                return biographyService.getOthersBiographyById(id);
            }

            @Override
            public Object onTimeout() throws InterruptedException {
                return null;
            }
        });
        return ok(execute);
    }


}
