package com.polymer.framework.security.core.interceptor;

import com.polymer.framework.common.annotation.SignatureCheck;
import com.polymer.framework.common.cache.RedisCache;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 签名校验拦截器
 *
 * @author polymer
 */
@Component
public class SignatureInterceptor implements HandlerInterceptor {
    @Resource
    private RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否为HandlerMethod（避免静态资源请求）
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 检查是否存在@SignatureCheck注解
        if (!method.isAnnotationPresent(SignatureCheck.class)) {
            return true;
        }

        // 从Header获取签名参数
        String signature = request.getHeader("Signature");
        String timestamp = request.getHeader("Timestamp");
        String nonce = request.getHeader("Nonce");
        String appId = request.getHeader("AppId");

        // 基础校验
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, appId)) {
            sendErrorResponse(response, "Missing required headers");
            return false;
        }

        // 根据appId获取对应的appSecret（例如从数据库或配置中心）
        String appSecret = getAppSecretByAppId(appId);

        // 生成服务端签名
        String serverSignature = generateSignature(request, timestamp, nonce, appSecret);

        // 校验签名
        if (!signature.equals(serverSignature)) {
            sendErrorResponse(response, "Invalid signature");
            return false;
        }

        // 校验时间戳有效期（示例：5分钟内）
        long currentTime = System.currentTimeMillis();
        long requestTime = Long.parseLong(timestamp);
        if (currentTime - requestTime > 5 * 60 * 1000) {
            sendErrorResponse(response, "Request expired");
            return false;
        }

        // 校验nonce唯一性（防止重放攻击）
        if (isNonceUsed(nonce, timestamp)) {
            sendErrorResponse(response, "Duplicate request");
            return false;
        }


        return true;
    }

    private String generateSignature(HttpServletRequest request, String timestamp, String nonce, String appSecret) {
        // 1. 获取所有请求参数（包括Query和Form）
        Map<String, String> params = new LinkedHashMap<>();

        // 添加固定参数
        params.put("timestamp", timestamp);
        params.put("nonce", nonce);
        params.put("appId", request.getHeader("AppId"));

        // 添加请求参数
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            params.put(name, request.getParameter(name));
        }

        // 2. 参数按ASCII码排序
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);

        // 3. 拼接参数
        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        sb.deleteCharAt(sb.length() - 1); // 删除最后一个&

        // 4. 拼接密钥
        sb.append(appSecret);

        // 5. 生成签名（示例使用SHA256）
        return DigestUtils.sha256Hex(sb.toString());
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("{\"code\":401, \"message\":\"" + message + "\"}");
    }

    // 需根据实际情况实现
    private String getAppSecretByAppId(String appId) {
        String key = CacheConstants.SYS_APPID_KEY + appId;
        Object value = redisCache.get(key);
        return (String) value;
    }

    private boolean isNonceUsed(String nonce, String timestamp) {
        // 实现nonce校验逻辑 Redis存储
        String redisKey = CacheConstants.SIGN_NONCE_KEY + nonce;
        return !redisCache.isAbsent(redisKey, timestamp);
    }
}
