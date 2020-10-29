package com.example.bio.service;

import com.example.bio.model.UserActiveToken;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface UserActiveTokenService extends IService<UserActiveToken> {
    /**
     * 向account_active_token表中添加记录
     * @param activeToken
     */
    void addToken(UserActiveToken activeToken);

    /**
     * 根据token查找
     * @param token
     * @return
     */
    UserActiveToken findByToken(@Param("token") String token);

    /**
     * 删除token记录
     * @param id
     */
    void removeToken(@Param("id") Long id);


}
