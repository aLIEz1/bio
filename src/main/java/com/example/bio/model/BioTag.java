package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("bio_tag")
@ApiModel(value="BioTag对象", description="")
public class BioTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签的id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("tag_name")
    private String tagName;

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


}
