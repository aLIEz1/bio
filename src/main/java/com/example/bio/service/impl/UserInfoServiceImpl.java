package com.example.bio.service.impl;

import com.example.bio.model.UserInfo;
import com.example.bio.mapper.UserInfoMapper;
import com.example.bio.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
