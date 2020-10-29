package com.example.bio.log;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangfuqi
 * @date 2020/10/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebLog {
    private String description;
    private String username;
    private Long startTime;
    private Integer spendTime;
    private String basePath;
    private String uri;
    private String url;
    private String method;
    private String ip;
    private Object parameter;
    private Object result;

}
