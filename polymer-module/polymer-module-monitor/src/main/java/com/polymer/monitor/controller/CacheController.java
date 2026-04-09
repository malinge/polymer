package com.polymer.monitor.controller;

import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.JsonUtils;
import com.polymer.monitor.vo.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 缓存监控
 *
 * @author Pure tea
 */
@RestController
@RequestMapping("monitor/cache")
public class CacheController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final static List<Cache> caches = new ArrayList<>();

    static {
        caches.add(new Cache(CacheConstants.SYS_TOKEN_KEY, "用户TOKEN"));
        caches.add(new Cache(CacheConstants.SYS_CAPTCHA_KEY, "验证码"));
        caches.add(new Cache(CacheConstants.SYS_USERID_KEY, "用户id"));
        caches.add(new Cache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
    }

    /**
     * Redis详情
     */
    @GetMapping("info")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<Map<String, Object>> getInfo() {
        Map<String, Object> result = new HashMap<>();
        // Step 1: 获取Redis详情
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
        result.put("info", info);
        // Step 2: 获取Key的数量
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);
        result.put("keyCount", dbSize);
        // Step 3: 获取请求次数
        List<Map<String, Object>> pieList = new ArrayList<>();
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandStats"));
        if (commandStats != null && commandStats.size() != 0) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, Object> data = new HashMap<>();
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.substring(key, 8));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",use"));
                pieList.add(data);
            });
        }
        result.put("commandStats", pieList);
        return Result.ok(result);
    }

    // 查询缓存名称列表
    @GetMapping("/getCacheName")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<List<Cache>> getCacheName() {
        return Result.ok(caches);
    }

    // 查询缓存键名列表
    @GetMapping("/getCacheKeys/{cacheName}")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<Set<String>> getCacheKeys(@PathVariable String cacheName) {
        Set<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        return Result.ok(cacheKeys);
    }

    // 查询缓存内容
    @GetMapping("/getCacheValue/{cacheName}/{cacheKey}")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<Cache> getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        Object cacheValue = redisTemplate.opsForValue().get(cacheKey);
        String json = JsonUtils.toJsonString(cacheValue);
        Cache sysCache = new Cache(cacheName, cacheKey, json);
        return Result.ok(sysCache);
    }

    // 清理指定名称缓存
    @DeleteMapping("/clearCacheName/{cacheName}")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<Cache> clearCacheName(@PathVariable String cacheName) {
        Collection<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        Assert.notNull(cacheKeys, "Key值为空");
        redisTemplate.delete(cacheKeys);
        return Result.ok();
    }

    // 清理指定键名缓存
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<String> clearCacheKey(@PathVariable String cacheKey) {
        redisTemplate.delete(cacheKey);
        return Result.ok();
    }

    // 清理全部缓存
    @DeleteMapping("/clearCacheAll")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<String> clearCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        Assert.notNull(cacheKeys, "Key值为空");
        redisTemplate.delete(cacheKeys);
        return Result.ok();
    }

}
