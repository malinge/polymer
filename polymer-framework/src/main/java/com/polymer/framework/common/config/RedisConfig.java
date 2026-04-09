package com.polymer.framework.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.polymer.framework.common.utils.ReflectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis配置
 *
 * @author polymer
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Key HashKey使用String序列化
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        template.setConnectionFactory(redisConnectionFactory);

        // 使用 JSON 序列化方式（库是 Jackson ），序列化 VALUE 。
        template.setValueSerializer(buildRedisSerializer());
        template.setHashValueSerializer(buildRedisSerializer());
        return template;
    }

    /**
     * 暴露为 Bean，供 CacheConfig 等其他组件复用
     */
    @Bean
    public RedisSerializer<Object> redisCacheSerializer() {
        return buildRedisSerializer();
    }

    public RedisSerializer<Object> buildRedisSerializer() {
        RedisSerializer<Object> json = RedisSerializer.json();
        // 解决 LocalDateTime 的序列化
        ObjectMapper objectMapper = ReflectUtils.getFieldValue(json, "mapper");
        assert objectMapper != null;
        objectMapper.registerModules(new JavaTimeModule());
        return json;
    }

    @Bean
    public DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(limitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 限流脚本
     * 使用 lua 脚本实现获取锁
     */
    private String limitScriptText() {
        return "local key = KEYS[1]\n" +
                "local count = tonumber(ARGV[1])\n" +
                "local time = tonumber(ARGV[2])\n" +
                "local current = redis.call('get', key);\n" +
                "if current and tonumber(current) > count then\n" +
                "    return tonumber(current);\n" +
                "end\n" +
                "current = redis.call('incr', key)\n" +
                "if tonumber(current) == 1 then\n" +
                "    redis.call('expire', key, time)\n" +
                "end\n" +
                "return tonumber(current);";
    }
}