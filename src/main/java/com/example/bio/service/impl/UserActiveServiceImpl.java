package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.exception.Asserts;
import com.example.bio.mapper.UserActiveMapper;
import com.example.bio.model.User;
import com.example.bio.model.UserActive;
import com.example.bio.service.UserActiveService;
import com.example.bio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Service
public class UserActiveServiceImpl extends ServiceImpl<UserActiveMapper, UserActive> implements UserActiveService {

    @Autowired
    private UserService userService;

    @Override
    public UserActive getCurrentUserActive() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        return getUserActiveByUserId(currentUser.getId());
    }

    @Override
    public UserActive getUserActiveByUserId(String userId) {
        QueryWrapper<UserActive> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("is_deleted", 0);
        return getOne(wrapper);
    }

    @Override
    public void initUserActive(String userId) {
        UserActive userActive = new UserActive();
        userActive.setUserId(Long.parseLong(userId));
        userActive.setBioNum(0);
        userActive.setCommentNum(0);
        userActive.setInvitationNum(0);
        save(userActive);
    }

    @Override
    public void incrementBioNum(String userId) {
        UpdateWrapper<UserActive> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId)
               .eq("is_deleted", 0)
               .setSql("bio_num = bio_num + 1");
        update(wrapper);
    }

    @Override
    public void incrementCommentNum(String userId) {
        UpdateWrapper<UserActive> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId)
               .eq("is_deleted", 0)
               .setSql("comment_num = comment_num + 1");
        update(wrapper);
    }
}
