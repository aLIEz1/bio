package com.lm.bio.cloud.common.web.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lm.bio.cloud.common.web.util.SnowFlakeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangfuqi
 * @date 2020/12/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6168451730219825953L;
    @ApiModelProperty(value = "唯一标识")
    @TableId(value = "id")
    private Long id= SnowFlakeUtil.nextId();

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "gmt_create")
    private Date gmtCreate;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "gmt_modified")
    private Date gmtModified;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableField(value = "is_deleted")
    private Integer delFlag = 0;
}
