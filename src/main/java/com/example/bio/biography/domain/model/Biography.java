package com.example.bio.biography.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Biography 聚合根。
 * <p>
 * 规则：
 * - 通过 tagIds（ID 引用）关联 BioTag，聚合间不直接持有对方对象。
 * - 展示时由 Application Service 负责组装 BioTag 列表。
 * - domain 层无任何 Spring / MBP 注解。
 */
public class Biography {

    private String id;
    private String ownerId;
    private String title;
    private String content;
    private String categoryId;
    private String categoryName;   // 冗余字段，写入时由 AppService 填充
    private String penName;
    private PrivacyLevel privacyLevel;
    private BiographyStatus status;
    private String views;
    private int enableComment;     // 0-允许 1-不允许
    private String note;
    private long commentNum;
    private long likes;
    private Set<String> tagIds = new HashSet<>();
    private Date gmtCreate;
    private Date gmtModified;
    private int delFlag;

    // ---- 业务行为 ----

    /** 点赞，返回新点赞数 */
    public long like() {
        this.likes = this.likes + 1;
        return this.likes;
    }

    /** 取消点赞，不低于 0 */
    public long unlike() {
        this.likes = Math.max(0, this.likes - 1);
        return this.likes;
    }

    /** 增加评论数 */
    public void incrementCommentNum() {
        this.commentNum = this.commentNum + 1;
    }

    /** 检查是否允许评论 */
    public boolean isCommentEnabled() {
        return this.enableComment == 0;
    }

    public boolean isPublic() {
        return privacyLevel == PrivacyLevel.PUBLIC && status == BiographyStatus.PUBLISHED;
    }

    // ---- getters / setters ----

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

    public PrivacyLevel getPrivacyLevel() { return privacyLevel; }
    public void setPrivacyLevel(PrivacyLevel privacyLevel) { this.privacyLevel = privacyLevel; }

    public BiographyStatus getStatus() { return status; }
    public void setStatus(BiographyStatus status) { this.status = status; }

    public String getViews() { return views; }
    public void setViews(String views) { this.views = views; }

    public int getEnableComment() { return enableComment; }
    public void setEnableComment(int enableComment) { this.enableComment = enableComment; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public long getCommentNum() { return commentNum; }
    public void setCommentNum(long commentNum) { this.commentNum = commentNum; }

    public long getLikes() { return likes; }
    public void setLikes(long likes) { this.likes = likes; }

    public Set<String> getTagIds() { return tagIds; }
    public void setTagIds(Set<String> tagIds) { this.tagIds = tagIds == null ? new HashSet<>() : tagIds; }

    public Date getGmtCreate() { return gmtCreate; }
    public void setGmtCreate(Date gmtCreate) { this.gmtCreate = gmtCreate; }

    public Date getGmtModified() { return gmtModified; }
    public void setGmtModified(Date gmtModified) { this.gmtModified = gmtModified; }

    public int getDelFlag() { return delFlag; }
    public void setDelFlag(int delFlag) { this.delFlag = delFlag; }
}
