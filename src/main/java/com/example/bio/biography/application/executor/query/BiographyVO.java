package com.example.bio.biography.application.executor.query;

import com.example.bio.biography.domain.model.Biography;
import com.example.bio.biography.domain.model.BioTag;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * Biography 的查询视图对象（原 BiographyView，改名 BiographyVO 对齐 COLA 命名）。
 */
public class BiographyVO {

    private String id;
    private String ownerId;
    private String title;
    private String content;
    private String categoryId;
    private String categoryName;
    private String penName;
    private Integer privacyLevel;
    private Integer status;
    private String views;
    private Integer enableComment;
    private String note;
    private Long commentNum;
    private Long likes;
    private Set<BioTag> tags = Collections.emptySet();
    private Date gmtCreate;
    private Date gmtModified;
    private Integer delFlag;

    public BiographyVO() {}

    public BiographyVO(Biography bio) {
        this.id = bio.getId();
        this.ownerId = bio.getOwnerId();
        this.title = bio.getTitle();
        this.content = bio.getContent();
        this.categoryId = bio.getCategoryId();
        this.categoryName = bio.getCategoryName();
        this.penName = bio.getPenName();
        this.privacyLevel = bio.getPrivacyLevel() == null ? null : bio.getPrivacyLevel().getCode();
        this.status = bio.getStatus() == null ? null : bio.getStatus().getCode();
        this.views = bio.getViews();
        this.enableComment = bio.getEnableComment();
        this.note = bio.getNote();
        this.commentNum = bio.getCommentNum();
        this.likes = bio.getLikes();
        this.gmtCreate = bio.getGmtCreate();
        this.gmtModified = bio.getGmtModified();
        this.delFlag = bio.getDelFlag();
    }

    public String getId() { return id; }
    public String getOwnerId() { return ownerId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public String getPenName() { return penName; }
    public Integer getPrivacyLevel() { return privacyLevel; }
    public Integer getStatus() { return status; }
    public String getViews() { return views; }
    public Integer getEnableComment() { return enableComment; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Long getCommentNum() { return commentNum; }
    public Long getLikes() { return likes; }
    public Set<BioTag> getTags() { return tags; }
    public void setTags(Set<BioTag> tags) { this.tags = tags; }
    public Date getGmtCreate() { return gmtCreate; }
    public Date getGmtModified() { return gmtModified; }
    public Integer getDelFlag() { return delFlag; }
}
