package com.polymer.framework.common.cache;

import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ScanOptions;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MultiLevelCache extends AbstractValueAdaptingCache {

    private static final Logger log = LoggerFactory.getLogger(MultiLevelCache.class);

    private final String name;
    private final Cache<Object, Object> caffeineCache;
    private final RedisTemplate<String, Object> redisTemplate;
    private final long caffeineTtlSec;
    private final long redisTtlSec;

    /**
     * @param allowNullValues 是否允许缓存 null（防穿透，建议 true）
     */
    protected MultiLevelCache(String name,
                              Cache<Object, Object> caffeineCache,
                              RedisTemplate<String, Object> redisTemplate,
                              long caffeineTtlSec,
                              long redisTtlSec,
                              boolean allowNullValues) {
        super(allowNullValues);
        this.name = name;
        this.caffeineCache = caffeineCache;
        this.redisTemplate = redisTemplate;
        this.caffeineTtlSec = caffeineTtlSec;
        this.redisTtlSec = redisTtlSec;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return caffeineCache;
    }

    /**
     * 生成 Redis 中实际存储的 key（添加缓存名前缀，避免不同缓存区域 key 冲突）
     */
    private String getRedisKey(Object key) {
        return name + ":" + key.toString();
    }

    @Override
    protected Object lookup(Object key) {
        // 1. 一级缓存
        Object value = caffeineCache.getIfPresent(key);
        if (value != null) {
            return value;
        }
        // 2. 二级缓存（带前缀）
        String redisKey = getRedisKey(key);
        try {
            value = redisTemplate.opsForValue().get(redisKey);
            if (value != null) {
                // 回填一级缓存（注意：Caffeine 的过期时间在构建时已统一设定，这里直接 put）
                caffeineCache.put(key, value);
                return value;
            }
        } catch (Exception e) {
            log.error("Redis 读取失败，key: {}, 降级为仅使用本地缓存", redisKey, e);
        }
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        // 写入一级
        caffeineCache.put(key, value);
        // 二级缓存（带前缀）
        String redisKey = getRedisKey(key);
        try {
            if (redisTtlSec > 0) {
                redisTemplate.opsForValue().set(redisKey, value, redisTtlSec, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(redisKey, value);
            }
        } catch (Exception e) {
            log.error("Redis 写入失败，key: {}", redisKey, e);
        }
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        // 先查缓存（调用父类的 get 方法，内部会调用 lookup）
        ValueWrapper wrapper = get(key);
        if (wrapper != null) {
            return (T) wrapper.get();
        }
        // 缓存未命中，执行 valueLoader（只执行一次）
        T value;
        try {
            value = valueLoader.call();
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        }
        // 存入两级缓存（允许 value 为 null）
        put(key, value);
        return value;
    }

    @Override
    public void evict(Object key) {
        caffeineCache.invalidate(key);
        // 二级缓存驱逐（带前缀）
        String redisKey = getRedisKey(key);
        try {
            redisTemplate.delete(redisKey);
        } catch (Exception e) {
            log.error("Redis 删除失败，key: {}", redisKey, e);
        }
    }

    @Override
    public void clear() {
        // 清空一级缓存
        caffeineCache.invalidateAll();
        // 清空二级缓存中属于该缓存区域的所有 key（使用前缀匹配）
        String pattern = name + ":*";
        try {
            Set<String> keys = scan(pattern);
            if (!keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.debug("清空 Redis 缓存区域 [{}]，共删除 {} 个 key", name, keys.size());
            }
        } catch (Exception e) {
            log.error("Redis 清空失败，缓存名: {}, pattern: {}", name, pattern, e);
        }
    }

    /**
     * 使用SCAN命令获取匹配的key,避免阻塞Redis
     * @param pattern 匹配模式
     * @return 匹配的key集合
     */
    private Set<String> scan(String pattern) {
        Set<String> keys = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(100).build();
        try (Cursor<String> cursor = redisTemplate.scan(options)) {
            while (cursor.hasNext()) {
                keys.add(cursor.next());
            }
        }
        return keys;
    }
}