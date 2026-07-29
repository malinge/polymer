package com.polymer.framework.security.core.cache;

import com.polymer.framework.common.cache.RedisCache;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.security.core.properties.SecurityProperties;
import com.polymer.framework.security.core.user.UserDetail;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 认证 Cache
 *
 * @author polymer
 */
@Component
public class TokenStoreCache {
    @Resource
    private RedisCache redisCache;
    @Resource
    private SecurityProperties securityProperties;

    public void saveUser(String accessToken, UserDetail user) {
        String key = CacheConstants.SYS_TOKEN_KEY + accessToken;
        redisCache.set(key, user, securityProperties.getAccessTokenExpire());
    }

    public void saveUser(String accessToken, UserDetail user, long expire) {
        String key =CacheConstants.SYS_TOKEN_KEY + accessToken;
        redisCache.set(key, user, expire);
    }

    public Long getExpire(String accessToken) {
        String key = CacheConstants.SYS_TOKEN_KEY + accessToken;

        return redisCache.getExpire(key);
    }


    public UserDetail getUser(String accessToken) {
        String key = CacheConstants.SYS_TOKEN_KEY + accessToken;
        return (UserDetail) redisCache.get(key);
    }

    public void deleteUser(String accessToken) {
        String key = CacheConstants.SYS_TOKEN_KEY + accessToken;
        redisCache.delete(key);
    }

    public List<String> getUserKeyList() {
        String pattern = CacheConstants.SYS_TOKEN_KEY +"*";
        Set<String> sets = redisCache.scan(pattern);
        return new ArrayList<>(sets);
    }

    public void saveAccessToken(Long userId, String accessToken) {
        String key = CacheConstants.SYS_USERID_KEY + userId;
        redisCache.set(key, accessToken, securityProperties.getAccessTokenExpire());
    }

    public String getAccessToken(Long userId) {
        String key = CacheConstants.SYS_USERID_KEY + userId;
        return (String) redisCache.get(key);
    }

    public boolean getKickOut() {
        return securityProperties.getKickOut();
    }
}
