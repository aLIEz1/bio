package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
@TableName("user_info")
@ApiModel(value="UserInfo对象", description="")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户信息id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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

    @ApiModelProperty(value = "记录创建时间")
    @TableField("gmt_create")
    private Date gmtCreate;

    @ApiModelProperty(value = "记录修改时间")
    @TableField("gmt_modified")
    private Date gmtModified;


}
