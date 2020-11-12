package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("role")
@ApiModel(value = "Role对象", description = "")
public class Role extends BaseEntity {

    @ApiModelProperty(value = "角色名")
    @TableField("role_name")
    private ERole roleName;


}
