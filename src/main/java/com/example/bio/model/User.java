package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.biography.domain.model.Biography;
import com.example.bio.common.api.BaseEntity;
import com.example.bio.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;
@TableName("user")
@ApiModel(value = "User对象", description = "")
public class User extends BaseEntity {

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "用户头像")
    @TableField(value = "avatar")
    private String avatar = CommonConstant.USER_DEFAULT_AVATAR;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "邀请码")
    @TableField("invitation_code")
    private String invitationCode;

    @ApiModelProperty(value = "积分")
    @TableField("points")
    private Integer points;

    @ApiModelProperty(value = "0-未锁定，1-已锁定")
    @TableField("is_locked")
    private Integer isLocked;

    @ApiModelProperty(value = "自传")
    @TableField(exist = false)
    private Set<Biography> biographies;

    @ApiModelProperty(value = "用户信息")
    @TableField(exist = false)
    private UserInfo userInfo;

    @ApiModelProperty(value = "用户活跃信息")
    @TableField(exist = false)
    private UserActive userActive;

    @ApiModelProperty(value = "用户角色")
    @TableField(exist = false)
    private Set<Role> roles;

    @ApiModelProperty(value = "用户好友")
    @TableField(exist = false)
    private Set<User> friends;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getInvitationCode() { return invitationCode; }
    public void setInvitationCode(String invitationCode) { this.invitationCode = invitationCode; }
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    public Integer getIsLocked() { return isLocked; }
    public void setIsLocked(Integer isLocked) { this.isLocked = isLocked; }
    public Set<Biography> getBiographies() { return biographies; }
    public void setBiographies(Set<Biography> biographies) { this.biographies = biographies; }
    public UserInfo getUserInfo() { return userInfo; }
    public void setUserInfo(UserInfo userInfo) { this.userInfo = userInfo; }
    public UserActive getUserActive() { return userActive; }
    public void setUserActive(UserActive userActive) { this.userActive = userActive; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public Set<User> getFriends() { return friends; }
    public void setFriends(Set<User> friends) { this.friends = friends; }
}
