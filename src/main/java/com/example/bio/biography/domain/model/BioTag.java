package com.example.bio.biography.domain.model;

import java.util.Date;

/**
 * BioTag 独立聚合根。
 * domain 层纯 POJO，无 Spring / MBP 注解。
 */
public class BioTag {

    private String id;
    private String tagName;
    private Date gmtCreate;
    private Date gmtModified;
    private int delFlag;

    public BioTag() {}

    public BioTag(String id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTagName() { return tagName; }
    public void setTagName(String tagName) { this.tagName = tagName; }

    public Date getGmtCreate() { return gmtCreate; }
    public void setGmtCreate(Date gmtCreate) { this.gmtCreate = gmtCreate; }

    public Date getGmtModified() { return gmtModified; }
    public void setGmtModified(Date gmtModified) { this.gmtModified = gmtModified; }

    public int getDelFlag() { return delFlag; }
    public void setDelFlag(int delFlag) { this.delFlag = delFlag; }
}
