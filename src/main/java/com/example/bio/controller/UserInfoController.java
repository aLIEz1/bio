package com.example.bio.controller;

import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.dto.UpdateUserInfoDto;
import com.example.bio.model.UserInfo;
import com.example.bio.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户详细信息控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Api(value = "user-info", tags = "用户信息模块")
@RestController
@RequestMapping("/api/user-info")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "获取当前用户详细信息")
    @GetMapping("/get")
    public Result<UserInfo> getCurrentUserInfo() {
        return ok(userInfoService.getCurrentUserInfo());
    }

    @ApiOperation(value = "根据用户id获取用户信息（游客可访问）", notes = "仅返回公开信息")
    @GetMapping("/getByUserId/{userId}")
    public Result<UserInfo> getUserInfoByUserId(@PathVariable("userId") String userId) {
        return ok(userInfoService.getUserInfoByUserId(userId));
    }

    @ApiOperation(value = "更新当前用户详细信息", notes = "支持局部更新：生日、组织、爱好、性别、电话、地址")
    @PutMapping("/update")
    public Result<Object> updateUserInfo(@RequestBody UpdateUserInfoDto dto) {
        userInfoService.updateUserInfo(dto);
        return ok("更新成功");
    }
}
