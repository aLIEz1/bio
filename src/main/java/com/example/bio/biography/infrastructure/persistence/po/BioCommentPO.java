package com.example.bio.biography.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("bio_comment")
public class BioCommentPO implements Serializable {

    @TableId("id")
    private String id;

    @TableField("bio_id")
    private String bioId;

    @TableField("user_id")
    private String userId;

    @TableField("comment_body")
    private String commentBody;

    @TableField("comment_status")
    private Integer commentStatus;

    @TableField("parent_id")
    private String parentId;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_modified")
    private Date gmtModified;

    @TableField("is_deleted")
    private Integer delFlag;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBioId() { return bioId; }
    public void setBioId(String bioId) { this.bioId = bioId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCommentBody() { return commentBody; }
    public void setCommentBody(String commentBody) { this.commentBody = commentBody; }

    public Integer getCommentStatus() { return commentStatus; }
    public void setCommentStatus(Integer commentStatus) { this.commentStatus = commentStatus; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public Date getGmtCreate() { return gmtCreate; }
    public void setGmtCreate(Date gmtCreate) { this.gmtCreate = gmtCreate; }

    public Date getGmtModified() { return gmtModified; }
    public void setGmtModified(Date gmtModified) { this.gmtModified = gmtModified; }

    public Integer getDelFlag() { return delFlag; }
    public void setDelFlag(Integer delFlag) { this.delFlag = delFlag; }
}
