package com.example.bio.log;

/**
 * @author zhangfuqi
 * @date 2020/10/27
 */
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

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Long getStartTime() { return startTime; }
    public void setStartTime(Long startTime) { this.startTime = startTime; }
    public Integer getSpendTime() { return spendTime; }
    public void setSpendTime(Integer spendTime) { this.spendTime = spendTime; }
    public String getBasePath() { return basePath; }
    public void setBasePath(String basePath) { this.basePath = basePath; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public Object getParameter() { return parameter; }
    public void setParameter(Object parameter) { this.parameter = parameter; }
    public Object getResult() { return result; }
    public void setResult(Object result) { this.result = result; }
}
