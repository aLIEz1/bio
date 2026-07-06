package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户好友关系实体
 *
 * @author zhangfuqi
 */
@Data
@TableName("user_friend_relation")
@ApiModel(value = "UserFriendRelation", description = "用户好友关系")
public class UserFriendRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id（复合主键之一）")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "好友用户id（复合主键之一）")
    @TableField("friend_id")
    private String friendId;

    @ApiModelProperty(value = "用户对好友的分组：GROUP_FRIEND / GROUP_RELATIVE")
    @TableField("user_group")
    private EGroup userGroup;

    @ApiModelProperty(value = "好友对用户的分组：GROUP_FRIEND / GROUP_RELATIVE")
    @TableField("friend_group")
    private EGroup friendGroup;

    @ApiModelProperty(value = "删除标志 0-未删除 1-已删除")
    @TableField("is_deleted")
    private Integer delFlag = 0;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("gmt_create")
    private Date gmtCreate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("gmt_modified")
    private Date gmtModified;

    // 非数据库字段：好友用户信息
    @ApiModelProperty(value = "好友用户信息")
    @TableField(exist = false)
    private User friendUser;
}
