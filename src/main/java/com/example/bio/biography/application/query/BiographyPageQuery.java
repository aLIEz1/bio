package com.example.bio.biography.application.query;

import com.alibaba.cola.dto.Query;

/**
 * 查询传记分页列表。
 * currentUserId != null 时为私人列表（owner 查自己的），null 时为公开列表。
 */
public class BiographyPageQuery extends Query {

    private String currentUserId;
    private String ownerId;
    private String categoryName;
    private Integer privacyLevel;
    private Integer status;
    private long current = 1;
    private long size = 10;

    public String getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(String currentUserId) { this.currentUserId = currentUserId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Integer getPrivacyLevel() { return privacyLevel; }
    public void setPrivacyLevel(Integer privacyLevel) { this.privacyLevel = privacyLevel; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public long getCurrent() { return current; }
    public void setCurrent(long current) { this.current = current; }

    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
}
