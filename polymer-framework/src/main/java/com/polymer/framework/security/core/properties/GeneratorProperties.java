package com.polymer.framework.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring boot starter properties
 * polymer@126.com
 *
 */
@ConfigurationProperties("polymer.generator")
public class GeneratorProperties {

    /**
     * 是否启用代码生成器
     */
    private boolean enabled = true;

    /**
     * 访问路径
     */
    private String path = "/generator/**";

    /**
     * 模板路径
     */
    private String template = "/template/polymer";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
