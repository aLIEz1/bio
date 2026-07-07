package com.example.bio.biography.domain.model;

import java.util.Date;

/**
 * BioCategory 独立聚合根。
 * domain 层纯 POJO，无 Spring / MBP 注解。
 */
public class BioCategory {

    private String id;
    private String categoryName;
    private Date gmtCreate;
    private Date gmtModified;
    private int delFlag;

    public BioCategory() {}

    public BioCategory(String id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Date getGmtCreate() { return gmtCreate; }
    public void setGmtCreate(Date gmtCreate) { this.gmtCreate = gmtCreate; }

    public Date getGmtModified() { return gmtModified; }
    public void setGmtModified(Date gmtModified) { this.gmtModified = gmtModified; }

    public int getDelFlag() { return delFlag; }
    public void setDelFlag(int delFlag) { this.delFlag = delFlag; }
}
