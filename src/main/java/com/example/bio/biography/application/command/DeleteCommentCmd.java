package com.example.bio.biography.application.command;

import com.alibaba.cola.dto.Command;

import java.util.Set;

public class DeleteCommentCmd extends Command {

    private String currentUserId;
    private String commentId;
    private Set<String> roleNames;

    public String getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(String currentUserId) { this.currentUserId = currentUserId; }

    public String getCommentId() { return commentId; }
    public void setCommentId(String commentId) { this.commentId = commentId; }

    public Set<String> getRoleNames() { return roleNames; }
    public void setRoleNames(Set<String> roleNames) { this.roleNames = roleNames; }

    public boolean isAdmin() { return roleNames != null && roleNames.contains("ROLE_ADMIN"); }
}
