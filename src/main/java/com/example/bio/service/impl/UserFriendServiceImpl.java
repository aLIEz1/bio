package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.dto.AddFriendDto;
import com.example.bio.dto.UpdateFriendGroupDto;
import com.example.bio.exception.Asserts;
import com.example.bio.mapper.UserFriendRelationMapper;
import com.example.bio.model.EGroup;
import com.example.bio.model.User;
import com.example.bio.model.UserFriendRelation;
import com.example.bio.service.UserFriendService;
import com.example.bio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 好友关系服务实现
 *
 * @author zhangfuqi
 */
@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendRelationMapper, UserFriendRelation> implements UserFriendService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFriendRelationMapper friendRelationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFriend(AddFriendDto dto) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        String currentUserId = currentUser.getId();
        String friendId = dto.getFriendId();

        if (currentUserId.equals(friendId)) {
            Asserts.fail("不能添加自己为好友");
        }

        // 检查是否已是好友
        QueryWrapper<UserFriendRelation> checkWrapper = new QueryWrapper<>();
        checkWrapper.eq("user_id", currentUserId)
                    .eq("friend_id", friendId)
                    .eq("is_deleted", 0);
        if (getOne(checkWrapper) != null) {
            Asserts.fail("已经是好友关系，无需重复添加");
        }

        // 双向建立好友关系
        UserFriendRelation rel1 = new UserFriendRelation();
        rel1.setUserId(currentUserId);
        rel1.setFriendId(friendId);
        rel1.setUserGroup(dto.getUserGroup());
        rel1.setFriendGroup(EGroup.GROUP_FRIEND);

        UserFriendRelation rel2 = new UserFriendRelation();
        rel2.setUserId(friendId);
        rel2.setFriendId(currentUserId);
        rel2.setUserGroup(EGroup.GROUP_FRIEND);
        rel2.setFriendGroup(dto.getUserGroup());

        save(rel1);
        save(rel2);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFriend(String friendId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        friendRelationMapper.removeFriend(currentUser.getId(), friendId);
    }

    @Override
    public List<UserFriendRelation> getFriendList() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        return friendRelationMapper.getFriendListByUserId(currentUser.getId());
    }

    @Override
    public void updateFriendGroup(UpdateFriendGroupDto dto) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        UpdateWrapper<UserFriendRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", currentUser.getId())
               .eq("friend_id", dto.getFriendId())
               .eq("is_deleted", 0)
               .set("user_group", dto.getUserGroup());
        update(wrapper);
    }
}
