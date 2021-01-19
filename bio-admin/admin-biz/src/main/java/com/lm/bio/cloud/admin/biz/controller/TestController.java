package com.lm.bio.cloud.admin.biz.controller;

import com.lm.bio.cloud.common.core.api.CommonResult;
import com.lm.bio.cloud.common.web.controller.BaseController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangfuqi
 * @date 2020/12/21
 */
@RestController
@RequestMapping("test")
@AllArgsConstructor
public class TestController extends BaseController {

    @GetMapping
    public CommonResult<?> testGet() {
        return ok("测试成功");
    }
}
