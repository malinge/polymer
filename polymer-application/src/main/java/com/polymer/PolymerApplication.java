package com.polymer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * EnableCaching 启用缓存支持
 * exclude = { DataSourceAutoConfiguration.class }: 禁用Spring Boot数据源的自动装配
 */
@EnableCaching
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PolymerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PolymerApplication.class, args);
    }
}