package com.example.bio.biography.application.command;

import com.alibaba.cola.dto.Command;

public class CommitCommentCmd extends Command {

    private String currentUserId;
    private String bioId;
    private String commentBody;
    private String parentId;

    public String getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(String currentUserId) { this.currentUserId = currentUserId; }

    public String getBioId() { return bioId; }
    public void setBioId(String bioId) { this.bioId = bioId; }

    public String getCommentBody() { return commentBody; }
    public void setCommentBody(String commentBody) { this.commentBody = commentBody; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }
}
