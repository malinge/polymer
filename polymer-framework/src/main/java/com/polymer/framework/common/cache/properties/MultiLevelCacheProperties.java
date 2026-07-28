package com.polymer.framework.common.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "polymer.cache")
public class MultiLevelCacheProperties {

    /**
     * Caffeine 缓存最大条目数
     */
    private long caffeineMaxSize = 1000;

    /**
     * Caffeine 缓存过期时间（秒）
     */
    private long caffeineTtlSec = 60;

    /**
     * Redis 缓存过期时间（秒）
     */
    private long redisTtlSec = 300;

    /**
     * 是否允许缓存空值（防止缓存穿透）
     */
    private boolean allowNullValues = true;

    // getter / setter 方法
    public long getCaffeineMaxSize() {
        return caffeineMaxSize;
    }

    public void setCaffeineMaxSize(long caffeineMaxSize) {
        this.caffeineMaxSize = caffeineMaxSize;
    }

    public long getCaffeineTtlSec() {
        return caffeineTtlSec;
    }

    public void setCaffeineTtlSec(long caffeineTtlSec) {
        this.caffeineTtlSec = caffeineTtlSec;
    }

    public long getRedisTtlSec() {
        return redisTtlSec;
    }

    public void setRedisTtlSec(long redisTtlSec) {
        this.redisTtlSec = redisTtlSec;
    }

    public boolean isAllowNullValues() {
        return allowNullValues;
    }

    public void setAllowNullValues(boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }
}