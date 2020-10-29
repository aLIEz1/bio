package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("role")
@ApiModel(value="Role对象", description="")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "角色名")
    @TableField("role_name")
    private ERole roleName;

    @ApiModelProperty(value = "0-未删除，1-已删除")
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "记录创建时间")
    @TableField("gmt_create")
    private Date gmtCreate;

    @ApiModelProperty(value = "记录修改时间")
    @TableField("gmt_modified")
    private Date gmtModified;

    @ApiModelProperty(value = "自传")
    private Set<Biography> biographies;

    @ApiModelProperty(value = "用户信息")
    private UserInfo userInfo;

    @ApiModelProperty(value = "用户活跃信息")
    private UserActive userActive;

    @ApiModelProperty(value = "用户角色")
    private Set<Role> roles;

    @ApiModelProperty(value = "用户好友")
    private Set<User> friends;


}
