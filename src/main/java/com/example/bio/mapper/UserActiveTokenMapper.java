package com.example.bio.mapper;

import com.example.bio.model.UserActiveToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
public interface UserActiveTokenMapper extends BaseMapper<UserActiveToken> {
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
