package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@ApiModel(value = "User对象", description = "")
public class User extends BaseEntity {

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

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


}
