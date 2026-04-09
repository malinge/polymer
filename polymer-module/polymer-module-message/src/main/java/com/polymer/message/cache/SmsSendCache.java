package com.polymer.message.cache;

import com.polymer.framework.common.cache.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 短信发送 Cache
 *
 * @author polymer
 */
@Service
public class SmsSendCache {
    @Resource
    private RedisCache redisCache;

    /**
     * 获取发送手机短信验证码KEY
     *
     * @param mobile 手机号
     * @return KEY
     */
    private String getCodeKey(String mobile) {
        return "message:sms:code" + mobile;
    }

    public void saveCode(String mobile, String code) {
        String key = getCodeKey(mobile);

        // 保存到Redis，有效期10分钟
        redisCache.set(key, code, 10 * 60);
    }

    public String getCode(String mobile) {
        String key = getCodeKey(mobile);
        return (String) redisCache.get(key);
    }

    public void deleteCode(String mobile) {
        String key = getCodeKey(mobile);
        redisCache.delete(key);
    }
}
