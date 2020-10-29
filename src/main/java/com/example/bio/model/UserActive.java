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
@TableName("user_active")
@ApiModel(value="UserActive对象", description="")
public class UserActive implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户活跃表id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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

    @ApiModelProperty(value = "记录创建时间")
    @TableField("gmt_create")
    private Date gmtCreate;

    @ApiModelProperty(value = "记录修改时间")
    @TableField("gmt_modified")
    private Date gmtModified;


}
