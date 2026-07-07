package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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

    @ApiModelProperty(value = "好友用户信息")
    @TableField(exist = false)
    private User friendUser;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getFriendId() { return friendId; }
    public void setFriendId(String friendId) { this.friendId = friendId; }
    public EGroup getUserGroup() { return userGroup; }
    public void setUserGroup(EGroup userGroup) { this.userGroup = userGroup; }
    public EGroup getFriendGroup() { return friendGroup; }
    public void setFriendGroup(EGroup friendGroup) { this.friendGroup = friendGroup; }
    public Integer getDelFlag() { return delFlag; }
    public void setDelFlag(Integer delFlag) { this.delFlag = delFlag; }
    public Date getGmtCreate() { return gmtCreate; }
    public void setGmtCreate(Date gmtCreate) { this.gmtCreate = gmtCreate; }
    public Date getGmtModified() { return gmtModified; }
    public void setGmtModified(Date gmtModified) { this.gmtModified = gmtModified; }
    public User getFriendUser() { return friendUser; }
    public void setFriendUser(User friendUser) { this.friendUser = friendUser; }
}
