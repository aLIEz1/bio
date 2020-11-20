package com.example.bio.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.EResult;
import com.example.bio.common.api.Result;
import com.example.bio.service.EsBiographyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangfuqi
 * @date 2020/11/19
 */
@Api(value = "ES控制器", tags = "ES模块")
@RestController
@RequestMapping("/api/esBiography")
public class EsBiographyController extends BaseController {

    @Autowired
    private EsBiographyService esBiographyService;

    @ApiOperation(value = "获取全部信息")
    @GetMapping("/getAll")
    public Result<?> getAll(){
        return ok(esBiographyService.getAll());
    }

    @ApiOperation(value = "导入到es")
    @PostMapping("/importAll")
    public Result<?> importAll() {
        int i = esBiographyService.importAll();
        return ok(EResult.SUCCESS, i);
    }

    @ApiOperation(value = "根据id删除")
    @DeleteMapping("/deleteById/{id}")
    public Result<?> deleteById(@PathVariable("id") String id) {
        if (StrUtil.isNotBlank(id)) {
            esBiographyService.delete(id);
            return ok("删除成功");
        } else {
            return fail("请输入正确的id");
        }
    }

    @ApiOperation(value = "根据id批量删除")
    @DeleteMapping("/deleteByIdBatches")
    public Result<?> deleteByIdBatches(@RequestParam List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return fail("参数不正确，检查后重试");
        } else {
            esBiographyService.deleteBatches(ids);
            return ok("删除成功");
        }
    }
}
