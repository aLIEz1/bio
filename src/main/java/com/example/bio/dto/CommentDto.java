package com.example.bio.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author zhangfuqi
 * @date 2020/11/3
 */
@Getter
@Setter
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
}
