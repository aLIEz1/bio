package com.example.bio.biography.application.command;

import com.alibaba.cola.dto.Command;

import java.util.Set;

public class UpdateBiographyCmd extends Command {

    private String currentUserId;
    private String id;
    private String title;
    private String content;
    private String categoryId;
    private String penName;
    private Integer privacyLevel;
    private Integer status;
    private Integer enableComment;
    private String note;
    private Set<String> tagIds;

    public String getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(String currentUserId) { this.currentUserId = currentUserId; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getPenName() { return penName; }
    public void setPenName(String penName) { this.penName = penName; }

    public Integer getPrivacyLevel() { return privacyLevel; }
    public void setPrivacyLevel(Integer privacyLevel) { this.privacyLevel = privacyLevel; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getEnableComment() { return enableComment; }
    public void setEnableComment(Integer enableComment) { this.enableComment = enableComment; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Set<String> getTagIds() { return tagIds; }
    public void setTagIds(Set<String> tagIds) { this.tagIds = tagIds; }
}
