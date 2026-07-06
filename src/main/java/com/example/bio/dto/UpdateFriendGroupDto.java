package com.example.bio.dto;

import com.example.bio.model.EGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改好友分组 DTO
 *
 * @author zhangfuqi
 */
@Getter
@Setter
@ApiModel(value = "UpdateFriendGroupDto", description = "修改好友分组请求体")
public class UpdateFriendGroupDto {

    @NotBlank(message = "好友用户id不能为空")
    @ApiModelProperty(value = "好友用户id", required = true)
    private String friendId;

    @NotNull(message = "分组不能为空")
    @ApiModelProperty(value = "新分组：GROUP_FRIEND / GROUP_RELATIVE", required = true)
    private EGroup userGroup;
}
