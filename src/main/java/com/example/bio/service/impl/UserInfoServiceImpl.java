package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.dto.UpdateUserInfoDto;
import com.example.bio.exception.Asserts;
import com.example.bio.mapper.UserInfoMapper;
import com.example.bio.model.User;
import com.example.bio.model.UserInfo;
import com.example.bio.service.UserInfoService;
import com.example.bio.service.UserService;
import org.springframework.beans.BeanUtils;
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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserService userService;

    @Override
    public UserInfo getCurrentUserInfo() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        return getUserInfoByUserId(currentUser.getId());
    }

    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("is_deleted", 0);
        return getOne(wrapper);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoDto dto) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            Asserts.fail("未登录，请先登录");
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", currentUser.getId()).eq("is_deleted", 0);
        UserInfo existing = getOne(queryWrapper);

        if (existing == null) {
            // 首次保存，自动创建
            UserInfo newInfo = new UserInfo();
            BeanUtils.copyProperties(dto, newInfo);
            newInfo.setUserId(currentUser.getId());
            save(newInfo);
        } else {
            // 更新已有记录
            UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", currentUser.getId()).eq("is_deleted", 0);
            UserInfo updateInfo = new UserInfo();
            BeanUtils.copyProperties(dto, updateInfo);
            update(updateInfo, updateWrapper);
        }
    }
}
