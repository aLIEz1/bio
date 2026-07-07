package com.example.bio.common.domain;

/**
 * @author zhangfuqi
 * @date 2020/10/27
 */
public class SwaggerProperties {
    private String apiBasePackage;
    private boolean enableSecurity;
    private String title;
    private String description;
    private String version;
    private String contactName;
    private String contactUrl;
    private String contactEmail;

    private SwaggerProperties() {}

    public String getApiBasePackage() { return apiBasePackage; }
    public boolean isEnableSecurity() { return enableSecurity; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getVersion() { return version; }
    public String getContactName() { return contactName; }
    public String getContactUrl() { return contactUrl; }
    public String getContactEmail() { return contactEmail; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final SwaggerProperties props = new SwaggerProperties();

        public Builder apiBasePackage(String v) { props.apiBasePackage = v; return this; }
        public Builder enableSecurity(boolean v) { props.enableSecurity = v; return this; }
        public Builder title(String v) { props.title = v; return this; }
        public Builder description(String v) { props.description = v; return this; }
        public Builder version(String v) { props.version = v; return this; }
        public Builder contactName(String v) { props.contactName = v; return this; }
        public Builder contactUrl(String v) { props.contactUrl = v; return this; }
        public Builder contactEmail(String v) { props.contactEmail = v; return this; }

        public SwaggerProperties build() { return props; }
    }
}
