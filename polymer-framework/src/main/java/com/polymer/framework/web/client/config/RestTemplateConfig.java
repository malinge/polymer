package com.polymer.framework.web.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    // 注入全局 ObjectMapper（确保序列化配置一致）
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // 使用 builder 创建基础配置
        RestTemplate restTemplate = builder
                .requestFactory(this::createRequestFactory)
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();

        // 自定义配置消息转换器
        configureMessageConverters(restTemplate.getMessageConverters());

        return restTemplate;
    }

    /**
     * 创建自定义的请求工厂
     */
    private ClientHttpRequestFactory createRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);
        return factory;
    }

    /**
     * 配置消息转换器，复用全局 ObjectMapper
     */
    private void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 1. 移除默认的 Jackson 转换器
        List<HttpMessageConverter<?>> convertersToRemove = new ArrayList<>();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                convertersToRemove.add(converter);
            }
        }
        converters.removeAll(convertersToRemove);

        // 2. 添加自定义的 UTF-8 字符串转换器（如果不存在）
        boolean hasStringConverter = false;
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                hasStringConverter = true;
                break;
            }
        }

        if (!hasStringConverter) {
            converters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        }

        // 3. 添加使用全局 ObjectMapper 的 Jackson 转换器
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

}
