package com.example.bio.biography.domain.gateway;

import java.util.List;

/**
 * 分页结果包装，替代 IPage 跨层传递。domain 层无 Spring / MBP 依赖。
 */
public class PageResult<T> {

    private final List<T> records;
    private final long total;
    private final long current;
    private final long size;

    public PageResult(List<T> records, long total, long current, long size) {
        this.records = records;
        this.total = total;
        this.current = current;
        this.size = size;
    }

    public List<T> getRecords() { return records; }
    public long getTotal() { return total; }
    public long getCurrent() { return current; }
    public long getSize() { return size; }
}
