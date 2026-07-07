package com.example.bio.biography.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("biography")
public class BiographyPO implements Serializable {

    @TableId("id")
    private String id;

    @TableField("owner_id")
    private String ownerId;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("category_id")
    private String categoryId;

    @TableField("category_name")
    private String categoryName;

    @TableField("pen_name")
    private String penName;

    @TableField("privacy_level")
    private Integer privacyLevel;

    @TableField("status")
    private Integer status;

    @TableField("views")
    private String views;

    @TableField("enable_comment")
    private Integer enableComment;

    @TableField("note")
    private String note;

    @TableField("comment_num")
    private Long commentNum;

    @TableField("likes")
    private Long likes;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_modified")
    private Date gmtModified;

    @TableField("is_deleted")
    private Integer delFlag;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getPenName() { return penName; }
    public void setPenName(String penName) { this.penName = penName; }

    public Integer getPrivacyLevel() { return privacyLevel; }
    public void setPrivacyLevel(Integer privacyLevel) { this.privacyLevel = privacyLevel; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getViews() { return views; }
    public void setViews(String views) { this.views = views; }

    public Integer getEnableComment() { return enableComment; }
    public void setEnableComment(Integer enableComment) { this.enableComment = enableComment; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Long getCommentNum() { return commentNum; }
    public void setCommentNum(Long commentNum) { this.commentNum = commentNum; }

    public Long getLikes() { return likes; }
    public void setLikes(Long likes) { this.likes = likes; }

    public Date getGmtCreate() { return gmtCreate; }
    public void setGmtCreate(Date gmtCreate) { this.gmtCreate = gmtCreate; }

    public Date getGmtModified() { return gmtModified; }
    public void setGmtModified(Date gmtModified) { this.gmtModified = gmtModified; }

    public Integer getDelFlag() { return delFlag; }
    public void setDelFlag(Integer delFlag) { this.delFlag = delFlag; }
}
