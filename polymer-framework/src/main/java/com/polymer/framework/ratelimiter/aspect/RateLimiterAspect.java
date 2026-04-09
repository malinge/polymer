package com.polymer.framework.ratelimiter.aspect;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.utils.HttpContextUtils;
import com.polymer.framework.common.utils.IpUtils;
import com.polymer.framework.ratelimiter.annotation.RateLimiter;
import com.polymer.framework.ratelimiter.enums.LimitType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 限流处理
 *
 * @author polymer
 */

@Aspect
@Component
public class RateLimiterAspect {
    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private RedisScript<Long> limitScript;


    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) {
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter, point);
        List<Object> keys = Collections.singletonList(combineKey);
        try {
            Long number = redisTemplate.execute(limitScript, keys, count, time);
            if (number.intValue() > count) {
                throw new ServiceException("访问过于频繁，请稍候再试");
            }
            log.info("限制请求'{}',当前请求'{}',缓存key'{}'", count, number.intValue(), combineKey);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }

    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder stringBuffer = new StringBuilder(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP) {
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            if (request != null) {
                stringBuffer.append(IpUtils.getIpAddr(request)).append("-");
            }
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }
}
