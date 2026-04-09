package com.polymer.framework.common.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MultiLevelCacheManager implements CacheManager {

    private final ConcurrentHashMap<String, Cache> cacheMap = new ConcurrentHashMap<>();
    private final RedisTemplate<String, Object> redisTemplate;
    private final long caffeineMaxSize;
    private final long caffeineTtlSec;
    private final long redisTtlSec;
    private final boolean allowNullValues;

    public MultiLevelCacheManager(RedisTemplate<String, Object> redisTemplate,
                                  long caffeineMaxSize,
                                  long caffeineTtlSec,
                                  long redisTtlSec,
                                  boolean allowNullValues) {
        this.redisTemplate = redisTemplate;
        this.caffeineMaxSize = caffeineMaxSize;
        this.caffeineTtlSec = caffeineTtlSec;
        this.redisTtlSec = redisTtlSec;
        this.allowNullValues = allowNullValues;
    }

    @Override
    public Cache getCache(String name) {
        return cacheMap.computeIfAbsent(name, this::createCache);
    }

    private Cache createCache(String name) {
        Caffeine<Object, Object> builder = Caffeine.newBuilder()
                .maximumSize(caffeineMaxSize)
                .expireAfterWrite(caffeineTtlSec, TimeUnit.SECONDS)
                .recordStats(); // 可选，开启统计
        com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache = builder.build();
        return new MultiLevelCache(name, caffeineCache, redisTemplate,
                caffeineTtlSec, redisTtlSec, allowNullValues);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }
}