package com.example.bio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bio.dto.AddFriendDto;
import com.example.bio.dto.UpdateFriendGroupDto;
import com.example.bio.model.UserFriendRelation;

import java.util.List;

/**
 * 好友关系服务接口
 *
 * @author zhangfuqi
 */
public interface UserFriendService extends IService<UserFriendRelation> {

    /**
     * 添加好友（双向建立关系）
     *
     * @param dto 添加好友请求
     */
    void addFriend(AddFriendDto dto);

    /**
     * 删除好友（双向软删除）
     *
     * @param friendId 好友用户id
     */
    void removeFriend(String friendId);

    /**
     * 获取当前用户的好友列表
     *
     * @return 好友关系列表
     */
    List<UserFriendRelation> getFriendList();

    /**
     * 修改好友分组
     *
     * @param dto 修改分组请求
     */
    void updateFriendGroup(UpdateFriendGroupDto dto);
}
