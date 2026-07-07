package com.example.bio.controller;

import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.model.UserActive;
import com.example.bio.user.application.query.UserActiveQuery;
import com.example.bio.user.application.service.UserActiveApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户活跃统计控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Api(value = "user-active", tags = "用户活跃统计模块")
@RestController
@RequestMapping("/api/user-active")
public class UserActiveController extends BaseController {

    private UserActiveApplicationService userActiveAppService;

    @Autowired
    public void setUserActiveAppService(UserActiveApplicationService userActiveAppService) {
        this.userActiveAppService = userActiveAppService;
    }

    @ApiOperation(value = "获取当前登录用户的活跃统计", notes = "包含发表传记数、评论数、邀请数")
    @GetMapping("/get")
    public Result<UserActive> getCurrentUserActive() {
        return ok(userActiveAppService.getCurrentUserActive().getData());
    }

    @ApiOperation(value = "根据用户id获取活跃统计（游客可访问）")
    @GetMapping("/getByUserId/{userId}")
    public Result<UserActive> getUserActiveByUserId(@PathVariable("userId") String userId) {
        UserActiveQuery query = new UserActiveQuery();
        query.setUserId(userId);
        return ok(userActiveAppService.getUserActiveByUserId(query).getData());
    }
}
