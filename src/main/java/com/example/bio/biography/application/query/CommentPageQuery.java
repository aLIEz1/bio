package com.example.bio.biography.application.query;

import com.alibaba.cola.dto.Query;

public class CommentPageQuery extends Query {

    private String bioId;
    private long current = 1;
    private long size = 10;

    public String getBioId() { return bioId; }
    public void setBioId(String bioId) { this.bioId = bioId; }

    public long getCurrent() { return current; }
    public void setCurrent(long current) { this.current = current; }

    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
}
