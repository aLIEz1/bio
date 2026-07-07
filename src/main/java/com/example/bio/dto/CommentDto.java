package com.example.bio.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author zhangfuqi
 * @date 2020/11/3
 */
public class CommentDto {

    @ApiModelProperty("自传id")
    @NotNull(message = "自传id不能为空")
    private String bioId;

    @ApiModelProperty("评论内容")
    @NotNull(message = "评论内容不能为空")
    private String commentBody;

    @ApiModelProperty("父级评论Id 若为首级评论则为0")
    @NotNull(message = "父级评论Id不能为空")
    private String parentId;

    public String getBioId() { return bioId; }
    public void setBioId(String bioId) { this.bioId = bioId; }
    public String getCommentBody() { return commentBody; }
    public void setCommentBody(String commentBody) { this.commentBody = commentBody; }
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }
}
