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
@TableName("biography")
@ApiModel(value = "Biography对象", description = "")
public class Biography implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自传id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "owner_id")
    private Long ownerId;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "正文")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "类别id")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "自传所属类别名称(冗余字段)")
    @TableField("category_name")
    private String categoryName;

    @ApiModelProperty(value = "笔名")
    @TableField("pen_name")
    private String penName;

    @ApiModelProperty(value = "0-公开，1-私密")
    @TableField("privacy_level")
    private Integer privacyLevel;

    @ApiModelProperty(value = "0-草稿，1-发布")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "阅读量")
    @TableField("views")
    private Long views;

    @ApiModelProperty(value = "0-允许评论，1-不允许评论")
    @TableField("enable_comment")
    private Integer enableComment;

    @ApiModelProperty(value = "备注")
    @TableField("note")
    private String note;

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

    @ApiModelProperty(value = "类别")
    @TableField(exist = false)
    private BioCategory category;

    @ApiModelProperty(value = "评论")
    @TableField(exist = false)
    private Set<BioComment> comments;

    @ApiModelProperty(value = "标签")
    @TableField(exist = false)
    private Set<BioTag> tags;
}
