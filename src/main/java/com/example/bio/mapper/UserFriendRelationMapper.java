package com.example.bio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bio.model.UserFriendRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友关系 Mapper 接口
 *
 * @author zhangfuqi
 */
public interface UserFriendRelationMapper extends BaseMapper<UserFriendRelation> {

    /**
     * 获取用户的好友列表（含好友用户信息）
     *
     * @param userId 用户id
     * @return 好友关系列表
     */
    List<UserFriendRelation> getFriendListByUserId(@Param("userId") String userId);

    /**
     * 删除好友关系（双向软删除）
     *
     * @param userId   用户id
     * @param friendId 好友id
     */
    void removeFriend(@Param("userId") String userId, @Param("friendId") String friendId);
}
