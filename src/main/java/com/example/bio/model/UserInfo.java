package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

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
@TableName("user_info")
@ApiModel(value = "UserInfo对象", description = "")
public class UserInfo extends BaseEntity {

    @ApiModelProperty(value = "关联的用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty(value = "所属组织")
    @TableField("organization")
    private String organization;

    @ApiModelProperty(value = "爱好")
    @TableField("hobby")
    private String hobby;

    @ApiModelProperty(value = "0-未设置，1-男，2-女")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty(value = "电话")
    @TableField("tel")
    private String tel;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

}
