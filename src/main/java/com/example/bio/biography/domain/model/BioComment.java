package com.example.bio.biography.domain.model;

import java.util.Date;

/**
 * BioComment — Biography 子实体，生命周期依附于传记。
 * domain 层纯 POJO，无 Spring / MBP 注解。
 */
public class BioComment {

    private String id;
    private String bioId;
    private String userId;
    private String commentBody;
    private int commentStatus;  // 0-待审核 1-通过
    private String parentId;
    private Date gmtCreate;
    private Date gmtModified;
    private int delFlag;

    // 查询时填充的子评论列表（非持久化字段）
    private java.util.List<BioComment> childComment;

    public BioComment() {}

    // ---- getters / setters ----

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBioId() { return bioId; }
    public void setBioId(String bioId) { this.bioId = bioId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCommentBody() { return commentBody; }
    public void setCommentBody(String commentBody) { this.commentBody = commentBody; }

    public int getCommentStatus() { return commentStatus; }
    public void setCommentStatus(int commentStatus) { this.commentStatus = commentStatus; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public Date getGmtCreate() { return gmtCreate; }
    public void setGmtCreate(Date gmtCreate) { this.gmtCreate = gmtCreate; }

    public Date getGmtModified() { return gmtModified; }
    public void setGmtModified(Date gmtModified) { this.gmtModified = gmtModified; }

    public int getDelFlag() { return delFlag; }
    public void setDelFlag(int delFlag) { this.delFlag = delFlag; }

    public java.util.List<BioComment> getChildComment() { return childComment; }
    public void setChildComment(java.util.List<BioComment> childComment) { this.childComment = childComment; }
}
