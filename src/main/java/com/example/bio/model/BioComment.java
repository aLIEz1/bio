package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = false)
@TableName("bio_comment")
@ApiModel(value = "BioComment对象", description = "")
public class BioComment extends BaseEntity {

    @ApiModelProperty(value = "自传id")
    @TableField("bio_id")
    private String bioId;

    @ApiModelProperty(value = "评论者的昵称")
    @TableField("commentator")
    private String commentator;

    @ApiModelProperty(value = "评论者邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "评论的内容")
    @TableField("comment_body")
    private String commentBody;

    @ApiModelProperty(value = "评论创建的时间")
    @TableField("gmt_comment_create")
    private Date gmtCommentCreate;

    @ApiModelProperty(value = "是否审核通过 0-未审核 1-审核通过")
    @TableField("comment_status")
    private Integer commentStatus;

    @ApiModelProperty(value = "回复内容")
    @TableField("reply_body")
    private String replyBody;

    @ApiModelProperty(value = "回复时间")
    @TableField("gmt_reply_create")
    private Date gmtReplyCreate;


}
