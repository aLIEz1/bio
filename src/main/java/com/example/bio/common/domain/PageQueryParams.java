package com.example.bio.common.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangfuqi
 * @date 2020/10/27
 */
public class PageQueryParams {
    private Page page;
    private Map<String, Object> conditions = new HashMap<>();

    public Page getPage() { return page; }
    public void setPage(Page page) { this.page = page; }
    public Map<String, Object> getConditions() { return conditions; }
    public void setConditions(Map<String, Object> conditions) { this.conditions = conditions; }
}
