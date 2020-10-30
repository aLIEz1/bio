package com.example.bio.common.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangfuqi
 * @date 2020/10/27
 */
@Getter
@Setter
public class PageQueryParams {
    private Page page;
    private Map<String, Object> conditions = new HashMap<>();
}
