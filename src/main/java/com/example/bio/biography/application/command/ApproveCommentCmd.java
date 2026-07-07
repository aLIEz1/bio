package com.example.bio.biography.application.command;

import com.alibaba.cola.dto.Command;

import java.util.List;

public class ApproveCommentCmd extends Command {

    private List<String> commentIds;

    public List<String> getCommentIds() { return commentIds; }
    public void setCommentIds(List<String> commentIds) { this.commentIds = commentIds; }
}
