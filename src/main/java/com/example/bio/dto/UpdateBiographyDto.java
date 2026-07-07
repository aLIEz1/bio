package com.example.bio.dto;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UpdateBiographyDto {

    @NotBlank
    private String id;

    private String title;
    private String content;
    private String categoryId;
    private String categoryName;
    private String penName;
    private Integer privacyLevel;
    private Integer status;
    private Integer enableComment;
    private String note;

    /** 前端传 [{id, tagName}, ...] 结构 */
    private Set<BiographyDto.TagRef> tags;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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
    public Integer getEnableComment() { return enableComment; }
    public void setEnableComment(Integer enableComment) { this.enableComment = enableComment; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Set<BiographyDto.TagRef> getTags() { return tags; }
    public void setTags(Set<BiographyDto.TagRef> tags) { this.tags = tags; }
}
