package com.example.bio.dto;

import com.example.bio.model.BioTag;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author zhangfuqi
 * @date 2020/10/28
 */
@Getter
@Setter
public class BiographyDto {

    private String ownerId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String categoryId;

    private String categoryName;

    private String penName;

    private Integer privacyLevel;

    private Integer status;

    private Integer enableComment;

    private String note;

    private Set<BioTag> tags;

}
