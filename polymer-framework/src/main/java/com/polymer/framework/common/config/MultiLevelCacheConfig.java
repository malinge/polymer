package com.polymer.framework.common.config;

import com.polymer.framework.common.cache.MultiLevelCacheManager;
import com.polymer.framework.common.cache.properties.MultiLevelCacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching// 启用 Spring 缓存抽象
public class MultiLevelCacheConfig {

    @Bean
    @Primary
    public CacheManager multiLevelCacheManager(RedisTemplate<String, Object> redisTemplate,
                                               MultiLevelCacheProperties cacheProperties) {
        return new MultiLevelCacheManager(
                redisTemplate,
                cacheProperties.getCaffeineMaxSize(),
                cacheProperties.getCaffeineTtlSec(),
                cacheProperties.getRedisTtlSec(),
                cacheProperties.isAllowNullValues()
        );
    }
}
