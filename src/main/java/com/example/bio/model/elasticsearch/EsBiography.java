package com.example.bio.model.elasticsearch;

import com.example.bio.model.BioTag;
import lombok.Data;
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
@Data
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


}
