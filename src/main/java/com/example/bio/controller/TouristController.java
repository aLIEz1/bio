package com.example.bio.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.example.bio.biography.application.executor.query.BiographyVO;
import com.example.bio.biography.application.query.BiographyByIdQuery;
import com.example.bio.biography.application.query.BiographyPageQuery;
import com.example.bio.biography.application.service.BiographyApplicationService;
import com.example.bio.common.annotation.RateLimiter;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.domain.PageQueryParams;
import com.example.bio.common.lock.Callback;
import com.example.bio.common.lock.RedisLockTemplateImpl;
import com.example.bio.model.elasticsearch.EsBiography;
import com.example.bio.service.EsBiographyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(value = "public", tags = "游客模块")
@RestController
@RequestMapping("/api/public")
public class TouristController extends BaseController {

    private BiographyApplicationService biographyAppService;
    private RedisLockTemplateImpl redisLockTemplate;
    private EsBiographyService esBiographyService;

    @Autowired
    public void setBiographyAppService(BiographyApplicationService biographyAppService) {
        this.biographyAppService = biographyAppService;
    }

    @Autowired
    public void setRedisLockTemplate(RedisLockTemplateImpl redisLockTemplate) {
        this.redisLockTemplate = redisLockTemplate;
    }

    @Autowired
    public void setEsBiographyService(EsBiographyService esBiographyService) {
        this.esBiographyService = esBiographyService;
    }

    @ApiOperation(value = "分页获取公开传记列表", notes = "conditions中可传入的数据有ownerId,表示其他用户的id, categoryName 类别名称" +
            "默认根据创建时间降序排序")
    @RateLimiter(rate = 1, rateInterval = 5000)
    @PostMapping("/getBiographiesPage")
    public Result<?> getPublicBiographyList(@RequestBody PageQueryParams pageQueryParams) {
        Object execute = redisLockTemplate.execute("touristGetBiographiesPage", 3, null, TimeUnit.SECONDS, new Callback() {
            @Override
            public Object onGetLock() {
                BiographyPageQuery query = new BiographyPageQuery();
                Map<String, Object> c = pageQueryParams.getConditions();
                if (c.containsKey("ownerId")) query.setOwnerId((String) c.get("ownerId"));
                if (c.containsKey("categoryName")) query.setCategoryName((String) c.get("categoryName"));
                query.setCurrent(pageQueryParams.getPage().getCurrent());
                query.setSize(pageQueryParams.getPage().getSize());
                return toPageMap(biographyAppService.getPublicBiographies(query));
            }

            @Override
            public Object onTimeout() {
                return null;
            }
        });
        return ok(execute);
    }

    @ApiOperation("获取传记详情")
    @RateLimiter(rate = 1, rateInterval = 5000)
    @GetMapping("/getBiographyById/{id}")
    public Result<Object> getBiographyById(@PathVariable("id") String id) {
        if (StrUtil.isBlank(id)) {
            return fail("请输入正确的id");
        }
        Object execute = redisLockTemplate.execute(id, 3, null, TimeUnit.SECONDS, new Callback() {
            @Override
            public Object onGetLock() {
                BiographyByIdQuery query = new BiographyByIdQuery();
                query.setId(id);
                SingleResponse<BiographyVO> resp = biographyAppService.getPublicBiographyById(query);
                return resp.getData();
            }

            @Override
            public Object onTimeout() {
                return null;
            }
        });
        return ok(execute);
    }

    @ApiOperation(value = "简单搜索")
    @GetMapping("/search")
    public Result<?> search(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsBiography> esBiographies = esBiographyService.search(keyword, pageNum, pageSize);
        return ok(esBiographies);
    }

    private Map<String, Object> toPageMap(PageResponse<BiographyVO> resp) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("records", resp.getData());
        map.put("total", resp.getTotalCount());
        map.put("current", resp.getPageIndex());
        map.put("size", resp.getPageSize());
        long size = resp.getPageSize();
        long total = resp.getTotalCount();
        map.put("pages", size > 0 ? (long) Math.ceil((double) total / size) : 0);
        return map;
    }
}
