package com.example.bio.model.elasticsearch;

import com.example.bio.biography.domain.model.BioTag;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author zhangfuqi
 * @date 2020/11/19
 */
@Document(indexName = "bio", replicas = 0)
public class EsBiography implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String ownerId;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String penName;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String title;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String categoryId;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String categoryName;

    @Field(type = FieldType.Text)
    private String note;

    private Long commentNum;
    private Long likes;
    private Integer privacyLevel;
    private Integer status;
    private String views;
    private Integer enableComment;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer delFlag = 0;

    @Field(type = FieldType.Nested)
    private Set<BioTag> tags;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    public String getPenName() { return penName; }
    public void setPenName(String penName) { this.penName = penName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Long getCommentNum() { return commentNum; }
    public void setCommentNum(Long commentNum) { this.commentNum = commentNum; }
    public Long getLikes() { return likes; }
    public void setLikes(Long likes) { this.likes = likes; }
    public Integer getPrivacyLevel() { return privacyLevel; }
    public void setPrivacyLevel(Integer privacyLevel) { this.privacyLevel = privacyLevel; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getViews() { return views; }
    public void setViews(String views) { this.views = views; }
    public Integer getEnableComment() { return enableComment; }
    public void setEnableComment(Integer enableComment) { this.enableComment = enableComment; }
    public Date getGmtCreate() { return gmtCreate; }
    public void setGmtCreate(Date gmtCreate) { this.gmtCreate = gmtCreate; }
    public Date getGmtModified() { return gmtModified; }
    public void setGmtModified(Date gmtModified) { this.gmtModified = gmtModified; }
    public Integer getDelFlag() { return delFlag; }
    public void setDelFlag(Integer delFlag) { this.delFlag = delFlag; }
    public Set<BioTag> getTags() { return tags; }
    public void setTags(Set<BioTag> tags) { this.tags = tags; }
}
