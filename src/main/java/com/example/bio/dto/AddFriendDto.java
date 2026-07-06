package com.example.bio.dto;

import com.example.bio.model.EGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 添加好友请求 DTO
 *
 * @author zhangfuqi
 */
@Getter
@Setter
@ApiModel(value = "AddFriendDto", description = "添加好友请求体")
public class AddFriendDto {

    @NotBlank(message = "好友用户id不能为空")
    @ApiModelProperty(value = "好友用户id", required = true)
    private String friendId;

    @ApiModelProperty(value = "分组：GROUP_FRIEND（朋友）/ GROUP_RELATIVE（家人），默认 GROUP_FRIEND")
    private EGroup userGroup = EGroup.GROUP_FRIEND;
}
