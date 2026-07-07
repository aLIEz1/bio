package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

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

    public LocalDateTime getExpiryDate() { return expiryDate; }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setExpiryDate(int minutes) {
        this.expiryDate = LocalDateTime.now().plusMinutes(minutes);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
