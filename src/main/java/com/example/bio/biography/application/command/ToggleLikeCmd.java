package com.example.bio.biography.application.command;

import com.alibaba.cola.dto.Command;

public class ToggleLikeCmd extends Command {

    private String currentUserId;
    private String bioId;

    public String getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(String currentUserId) { this.currentUserId = currentUserId; }

    public String getBioId() { return bioId; }
    public void setBioId(String bioId) { this.bioId = bioId; }
}
