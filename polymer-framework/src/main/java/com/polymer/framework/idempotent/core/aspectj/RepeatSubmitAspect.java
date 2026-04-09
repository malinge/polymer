package com.polymer.framework.idempotent.core.aspectj;

import com.polymer.framework.common.cache.RedisCache;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.HttpContextUtils;
import com.polymer.framework.common.utils.JsonUtils;
import com.polymer.framework.common.utils.Md5Utils;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.idempotent.core.annotation.RepeatSubmit;
import com.polymer.framework.security.core.utils.TokenUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 防止重复提交(参考美团GTIS防重系统)
 * 四层防重保障：
 *  接口维度：URL区分不同接口
 *  用户维度：Token区分不同用户
 *  参数维度：参数MD5区分不同请求内容
 *  时间维度：Redis TTL控制时间窗口
 * @author polymer
 */
@Aspect
public class RepeatSubmitAspect {
    @Resource
    private RedisCache redisCache;

    private static final ThreadLocal<String> KEY_CACHE = new ThreadLocal<>();

    @Before("@annotation(repeatSubmit)")
    public void doBefore(JoinPoint point, RepeatSubmit repeatSubmit) {
        // 如果注解不为0 则使用注解数值
        long interval = repeatSubmit.timeUnit().toMillis(repeatSubmit.interval());

        // 安全校验：最小间隔1秒
        if (interval < 1000) {
            throw new ServiceException("重复提交间隔时间不能小于'1'秒");
        }
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String nowParams = argsArrayToString(point.getArgs()); // 参数序列化

        if (request == null) {
            throw new ServiceException("请求异常");
        }
        // 构建防重Key的三要素
        String url = request.getRequestURI(); // 请求地址
        String submitKey = StringUtils.trimToEmpty(TokenUtils.getAccessToken(request));  // 用户令牌
        submitKey = Md5Utils.hash(submitKey + ":" + nowParams); // MD5签名

        String cacheRepeatKey = CacheConstants.REPEAT_SUBMIT_KEY + url + submitKey;

        // Redis检查与设置
        if (!redisCache.hasKey(cacheRepeatKey)) {
            long second = repeatSubmit.timeUnit().toSeconds(interval);
            redisCache.set(cacheRepeatKey, "", second); // 设置空值，过期时间
            KEY_CACHE.set(cacheRepeatKey); // 线程绑定
        } else {
            throw new ServiceException(repeatSubmit.message());  // 重复提交异常
        }
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(repeatSubmit)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Object jsonResult) {
        if (jsonResult instanceof Result<?>) {
            Result<?> res = (Result<?>) jsonResult;
            try {
                // 关键逻辑：仅当业务失败时才删除防重锁
                if (res.getCode() == Result.ok().getCode()) {
                    return;  // 成功不删除，保持防重效果
                }
                redisCache.delete(KEY_CACHE.get()); // 失败删除，允许重试
            } finally {
                KEY_CACHE.remove(); // 清理线程局部变量
            }
        }
    }

    /**
     * 拦截异常操作
     * 系统异常时应允许用户重新提交
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "@annotation(repeatSubmit)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Exception e) {
        redisCache.delete(KEY_CACHE.get()); // 异常时立即释放防重锁
        KEY_CACHE.remove();
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringJoiner params = new StringJoiner(" ");
        if (StringUtils.isEmpty(paramsArray)) {
            return params.toString();
        }
        for (Object o : paramsArray) {
            if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                params.add(JsonUtils.toJsonString(o));
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     * 排除类型参数： MultipartFile：文件上传对象；
     * HttpServletRequest/Response：Servlet对象；
     * BindingResult：验证结果对象
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return MultipartFile.class.isAssignableFrom(clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.values()) {
                return value instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

}
