package com.polymer.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * SSE长链接工具类
 */
public class SseEmitterUtil {
    private static final Logger log = LoggerFactory.getLogger(SseEmitterUtil.class);

    /**
     * 使用map对象，便于根据userId来获取对应的SseEmitter，或者放redis里面
     */
    private final static Map<Long, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    public static SseEmitter connect(Long userId) {
        // 设置超时时间，0表示不过期。默认30S，超时时间未完成会抛出异常：AsyncRequestTimeoutException
        SseEmitter sseEmitter = new SseEmitter(0L);

        // 注册回调
        sseEmitter.onCompletion(completionCallBack(userId));
        sseEmitter.onError(errorCallBack(userId));
        sseEmitter.onTimeout(timeoutCallBack(userId));
        sseEmitterMap.put(userId, sseEmitter);

        log.info("创建新的 SSE 连接，当前用户 {}, 连接总数 {}", userId, sseEmitterMap.size());
        return sseEmitter;
    }

    /**
     * 给制定用户发送消息
     *
     * @param userId     指定用户名
     * @param sseMessage 消息体
     */
    public static void sendMessage(Long userId, String sseMessage) {
        if (sseEmitterMap.containsKey(userId)) {
            try {
                sseEmitterMap.get(userId).send(sseMessage);
                log.info("用户 {} 推送消息 {}", userId, sseMessage);
            } catch (IOException e) {
                log.error("用户 {} 推送消息异常", userId, e);
                removeUser(userId);
            }
        } else {
            log.error("消息推送 用户 {} 不存在，链接总数 {}", userId, sseEmitterMap.size());
        }
    }

    /**
     * 群发消息
     */
    public static void batchSendMessage(String message, List<Long> ids) {
        ids.forEach(userId -> sendMessage(userId, message));
    }

    /**
     * 群发所有人
     */
    public static void batchSendMessage(String message) {
        sseEmitterMap.forEach((k, v) -> {
            try {
                v.send(message, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                log.error("用户 {} 推送异常", k, e);
                removeUser(k);
            }
        });
    }

    /**
     * 移除用户连接
     *
     * @param userId 用户 ID
     */
    public static void removeUser(Long userId) {
        if (sseEmitterMap.containsKey(userId)) {
            sseEmitterMap.get(userId).complete();
            sseEmitterMap.remove(userId);
            log.info("移除用户 {}, 剩余连接 {}", userId, sseEmitterMap.size());
        } else {
            log.error("消息推送 用户 {} 已被移除，剩余连接 {}", userId, sseEmitterMap.size());
        }
    }

    /**
     * 获取当前连接信息
     *
     * @return 所有的连接用户
     */
    public static List<Long> getIds() {
        return new ArrayList<>(sseEmitterMap.keySet());
    }

    /**
     * 获取当前的连接数量
     *
     * @return 当前的连接数量
     */
    public static int getUserCount() {
        return sseEmitterMap.size();
    }

    private static Runnable completionCallBack(Long userId) {
        return () -> {
            log.info("用户 {} 结束连接", userId);
        };
    }

    private static Runnable timeoutCallBack(Long userId) {
        return () -> {
            log.error("用户 {} 连接超时", userId);
            removeUser(userId);
        };
    }

    private static Consumer<Throwable> errorCallBack(Long userId) {
        return throwable -> {
            log.error("用户 {} 连接异常", userId);
            removeUser(userId);
        };
    }
}
