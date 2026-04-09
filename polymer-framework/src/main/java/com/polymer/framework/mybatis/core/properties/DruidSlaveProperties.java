package com.polymer.framework.mybatis.core.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * druid Master 配置属性
 */
@Configuration
public class DruidSlaveProperties {
    @Value("${spring.datasource.druid.slave.url}")
    private String url;
    @Value("${spring.datasource.druid.slave.username}")
    private String username;
    @Value("${spring.datasource.druid.slave.password}")
    private String password;
    @Value("${spring.datasource.druid.slave.enabled}")
    private boolean enabled;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
