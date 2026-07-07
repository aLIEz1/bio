package com.example.bio.controller;

import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.model.UserFriendRelation;
import com.example.bio.social.application.command.AddFriendCmd;
import com.example.bio.social.application.command.RemoveFriendCmd;
import com.example.bio.social.application.command.UpdateFriendGroupCmd;
import com.example.bio.social.application.query.FriendListQuery;
import com.example.bio.social.application.service.FriendApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 好友关系控制器
 *
 * @author zhangfuqi
 */
@Api(value = "friend", tags = "好友模块")
@RestController
@RequestMapping("/api/friend")
public class FriendController extends BaseController {

    private FriendApplicationService friendAppService;

    @Autowired
    public void setFriendAppService(FriendApplicationService friendAppService) {
        this.friendAppService = friendAppService;
    }

    @ApiOperation(value = "添加好友", notes = "双向建立好友关系")
    @PostMapping("/add")
    public Result<Object> addFriend(@RequestBody @Valid AddFriendCmd cmd) {
        friendAppService.addFriend(cmd);
        return ok("添加好友成功");
    }

    @ApiOperation(value = "删除好友", notes = "双向软删除好友关系")
    @DeleteMapping("/remove/{friendId}")
    public Result<Object> removeFriend(@PathVariable("friendId") String friendId) {
        RemoveFriendCmd cmd = new RemoveFriendCmd();
        cmd.setFriendId(friendId);
        friendAppService.removeFriend(cmd);
        return ok("删除好友成功");
    }

    @ApiOperation(value = "获取好友列表", notes = "返回当前用户的全部好友及其基本信息")
    @GetMapping("/list")
    public Result<List<UserFriendRelation>> getFriendList() {
        FriendListQuery query = new FriendListQuery();
        return ok(friendAppService.getFriendList(query).getData());
    }

    @ApiOperation(value = "修改好友分组", notes = "分组：GROUP_FRIEND（朋友）/ GROUP_RELATIVE（家人）")
    @PutMapping("/updateGroup")
    public Result<Object> updateFriendGroup(@RequestBody @Valid UpdateFriendGroupCmd cmd) {
        friendAppService.updateFriendGroup(cmd);
        return ok("修改分组成功");
    }
}
