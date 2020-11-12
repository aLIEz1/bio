package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

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
@TableName("bio_comment")
@ApiModel(value = "BioComment对象", description = "")
public class BioComment extends BaseEntity {

    @ApiModelProperty(value = "自传id")
    @TableField("bio_id")
    private String bioId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "评论的内容")
    @TableField("comment_body")
    private String commentBody;

    @ApiModelProperty(value = "是否审核通过 0-未审核 1-审核通过")
    @TableField("comment_status")
    private Integer commentStatus;

    @ApiModelProperty("父评论id")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "子评论")
    @TableField(exist = false)
    private List<BioComment> childComment;

}
