package com.polymer.framework.web.websocket.core;

import com.polymer.framework.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.polymer.framework.common.enums.WebSocketMessageTypeEnum.PING;
import static com.polymer.framework.common.enums.WebSocketMessageTypeEnum.PONG;

public class MyWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);
    private static final ConcurrentHashMap<String, CopyOnWriteArraySet<WebSocketSession>> userSessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = getUserIdFromSession(session); // 假设有一个方法可以从session中获取用户ID
        CopyOnWriteArraySet<WebSocketSession> sessions = userSessionMap.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>());
        sessions.add(session);
        log.info("用户{}连接建立，当前在线会话数：{}", getUserNameSession(session), sessions.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        //String userId = getUserIdFromSession(session);
        String userName = getUserNameSession(session);
        String payload = message.getPayload();
        log.info("收到用户{}的消息：{}", userName, payload);
        // 处理消息，例如广播给所有在线用户或特定用户
        NotifyMessageDTO notifyMessageDTO = JsonUtils.parseObject(payload, NotifyMessageDTO.class);
        assert notifyMessageDTO != null;
        if (PING.name().equalsIgnoreCase(notifyMessageDTO.getType())) {
            try {
                NotifyMessageDTO sendDTO = new NotifyMessageDTO();
                sendDTO.setType(PONG.name());
                sendDTO.setData(System.currentTimeMillis());
                String jsonString = JsonUtils.toJsonString(sendDTO);
                assert jsonString != null;
                session.sendMessage(new TextMessage(jsonString));
                log.info("回复用户{}的消息：{}", userName, jsonString);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = getUserIdFromSession(session);
        CopyOnWriteArraySet<WebSocketSession> sessions = userSessionMap.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            log.info("用户{}连接关闭，当前在线会话数：{}", getUserNameSession(session), sessions.size());
        }
    }

    // 辅助方法：从session中获取用户ID
    private String getUserIdFromSession(WebSocketSession session) {
        Long userId = (Long) session.getAttributes().get("userId");
        return String.valueOf(userId);
    }

    // 辅助方法：从session中获取用户名称
    private String getUserNameSession(WebSocketSession session) {
        return (String) session.getAttributes().get("userName");
    }

    // 广播消息给指定用户
    public static void sendMessageToUser(String userId, String message) throws IOException {
        CopyOnWriteArraySet<WebSocketSession> sessions = userSessionMap.get(userId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        } else {
            log.warn("用户{}不在线，无法发送消息", userId);
        }
    }
}
