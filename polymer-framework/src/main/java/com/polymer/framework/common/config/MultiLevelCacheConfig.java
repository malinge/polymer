package com.polymer.framework.common.config;

import com.polymer.framework.common.cache.MultiLevelCacheManager;
import org.springframework.beans.factory.annotation.Value;
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
    public CacheManager multiLevelCacheManager(RedisTemplate<String, Object> redisTemplate) {
        long caffeineMaxSize = 1000;
        long redisTtlSec = 300;
        long caffeineTtlSec = 60;
        // allowNullValues = true 防止缓存穿透
        return new MultiLevelCacheManager(redisTemplate, caffeineMaxSize, caffeineTtlSec, redisTtlSec, true);
    }
}
