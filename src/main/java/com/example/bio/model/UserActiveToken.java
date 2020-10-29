package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("user_active_token")
@ApiModel(value="UserActiveToken对象", description="")
public class UserActiveToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "激活用户的id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "过期时间")
    @TableField("expiry_date")
    private LocalDateTime expiryDate;

    @TableField("token")
    private String token;

    @ApiModelProperty(value = "关联用户表id")
    private User user;

    @ApiModelProperty(value = "记录创建时间")
    @TableField("gmt_create")
    private Date gmtCreate;

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
