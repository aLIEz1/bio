package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@TableName("user_active")
@ApiModel(value = "UserActive对象", description = "")
public class UserActive extends BaseEntity {

    @ApiModelProperty(value = "关联用户表id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "发表自传数量")
    @TableField("bio_num")
    private Integer bioNum;

    @ApiModelProperty(value = "评论数量")
    @TableField("comment_num")
    private Integer commentNum;

    @ApiModelProperty(value = "邀请数量")
    @TableField("Invitation_num")
    private Integer invitationNum;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getBioNum() { return bioNum; }
    public void setBioNum(Integer bioNum) { this.bioNum = bioNum; }
    public Integer getCommentNum() { return commentNum; }
    public void setCommentNum(Integer commentNum) { this.commentNum = commentNum; }
    public Integer getInvitationNum() { return invitationNum; }
    public void setInvitationNum(Integer invitationNum) { this.invitationNum = invitationNum; }
}
