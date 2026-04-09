package com.polymer.framework.web.websocket.config;

import com.polymer.framework.security.core.cache.TokenStoreCache;
import com.polymer.framework.web.websocket.core.MyWebSocketHandler;
import com.polymer.framework.web.websocket.core.interceptor.CustomHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * 启用WebSocket
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    private TokenStoreCache tokenStoreCache;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/ws")
                .addInterceptors(new CustomHandshakeInterceptor(tokenStoreCache))
                .setAllowedOrigins("*");
    }
}
