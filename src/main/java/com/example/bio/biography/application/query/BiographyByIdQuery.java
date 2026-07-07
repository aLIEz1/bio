package com.example.bio.biography.application.query;

import com.alibaba.cola.dto.Query;

/**
 * 按 id 查询单篇传记。
 * currentUserId != null 时为私人查询（需校验所有权），null 时为公开访问。
 */
public class BiographyByIdQuery extends Query {

    private String id;
    private String currentUserId;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(String currentUserId) { this.currentUserId = currentUserId; }
}
