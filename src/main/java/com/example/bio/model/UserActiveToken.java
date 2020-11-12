package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

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
@TableName("user_active_token")
@ApiModel(value = "UserActiveToken对象", description = "")
public class UserActiveToken extends BaseEntity {

    @ApiModelProperty(value = "过期时间")
    @TableField("expiry_date")
    private LocalDateTime expiryDate;

    @TableField("token")
    private String token;

    @ApiModelProperty(value = "关联用户表id")
    @TableField(exist = false)
    private User user;

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setExpiryDate(int minutes) {
        expiryDate = LocalDateTime.now().plusMinutes(minutes);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }


}
