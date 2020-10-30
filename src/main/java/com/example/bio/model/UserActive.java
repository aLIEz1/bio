package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.base.BaseEntity;
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
@TableName("user_active")
@ApiModel(value = "UserActive对象", description = "")
public class UserActive extends BaseEntity {

    @ApiModelProperty(value = "关联用户表id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "发表自传数量")
    @TableField("bio_num")
    private Integer bioNum;

    @ApiModelProperty(value = "评论数量")
    @TableField("comment_num")
    private Integer commentNum;

    @ApiModelProperty(value = "邀请数量")
    @TableField("Invitation_num")
    private Integer invitationNum;
}
