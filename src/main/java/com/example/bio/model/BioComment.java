package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("bio_comment")
@ApiModel(value="BioComment对象", description="")
public class BioComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "自传id")
    @TableField("bio_id")
    private Long bioId;

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

    @ApiModelProperty(value = "0-未删除，1-已删除")
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;


}
