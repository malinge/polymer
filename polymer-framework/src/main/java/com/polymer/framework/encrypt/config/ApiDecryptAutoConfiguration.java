package com.polymer.framework.encrypt.config;

import com.polymer.framework.encrypt.core.filter.CryptoFilter;
import com.polymer.framework.encrypt.core.properties.ApiDecryptProperties;
import com.polymer.framework.web.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * api 解密自动配置
 *
 * @author polymer
 */
@Configuration
@EnableConfigurationProperties(ApiDecryptProperties.class)
@ConditionalOnProperty(prefix = "polymer.api-decrypt", value = "enabled")
public class ApiDecryptAutoConfiguration {

    @Bean
    public FilterRegistrationBean<CryptoFilter> cryptoFilterRegistration(ApiDecryptProperties properties, GlobalExceptionHandler globalExceptionHandler) {
        FilterRegistrationBean<CryptoFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new CryptoFilter(properties, globalExceptionHandler));
        registration.addUrlPatterns("/*");
        registration.setName("cryptoFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }
}
