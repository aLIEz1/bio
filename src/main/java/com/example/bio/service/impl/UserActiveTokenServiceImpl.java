package com.example.bio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bio.mapper.UserActiveTokenMapper;
import com.example.bio.model.UserActiveToken;
import com.example.bio.service.UserActiveTokenService;
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
public class UserActiveTokenServiceImpl extends ServiceImpl<UserActiveTokenMapper, UserActiveToken> implements UserActiveTokenService {

    private UserActiveTokenMapper tokenMapper;

    @Autowired
    public void setTokenMapper(UserActiveTokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    @Override
    public void addToken(UserActiveToken activeToken) {
        tokenMapper.addToken(activeToken);
    }

    @Override
    public UserActiveToken findByToken(String token) {
        return tokenMapper.findByToken(token);
    }

    @Override
    public void removeToken(String id) {
        tokenMapper.removeToken(id);
    }
}
