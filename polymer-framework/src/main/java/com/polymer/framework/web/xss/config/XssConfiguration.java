package com.polymer.framework.web.xss.config;

import com.polymer.framework.web.xss.core.XssFilter;
import com.polymer.framework.web.xss.core.XssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;

/**
 * XSS 配置文件
 *
 * @author polymer
 */
@Configuration
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(prefix = "polymer.xss", value = "enabled")
public class XssConfiguration {
    @Resource
    private PathMatcher pathMatcher;

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter(XssProperties properties) {
        FilterRegistrationBean<XssFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new XssFilter(properties, pathMatcher));
        bean.setOrder(Integer.MAX_VALUE);
        bean.setName("xssFilter");

        return bean;
    }
}
