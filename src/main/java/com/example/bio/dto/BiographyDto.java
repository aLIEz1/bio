package com.example.bio.dto;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class BiographyDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String categoryId;

    private String penName;
    private Integer privacyLevel;
    private Integer status;
    private Integer enableComment;
    private String note;

    /** 前端传 [{id, tagName}, ...] 结构 */
    private Set<TagRef> tags;

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
    public Set<TagRef> getTags() { return tags; }
    public void setTags(Set<TagRef> tags) { this.tags = tags; }

    public static class TagRef {
        private String id;
        private String tagName;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getTagName() { return tagName; }
        public void setTagName(String tagName) { this.tagName = tagName; }
    }
}
