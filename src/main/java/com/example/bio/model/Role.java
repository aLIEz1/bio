package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@TableName("role")
@ApiModel(value = "Role对象", description = "")
public class Role extends BaseEntity {

    @ApiModelProperty(value = "角色名")
    @TableField("role_name")
    private ERole roleName;

    public ERole getRoleName() { return roleName; }
    public void setRoleName(ERole roleName) { this.roleName = roleName; }
}
