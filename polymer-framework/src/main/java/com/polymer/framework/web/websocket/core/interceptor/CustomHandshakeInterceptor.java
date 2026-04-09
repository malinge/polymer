package com.polymer.framework.web.websocket.core.interceptor;

import com.polymer.framework.security.core.cache.TokenStoreCache;
import com.polymer.framework.security.core.user.UserDetail;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    private final TokenStoreCache tokenStoreCache;

    public CustomHandshakeInterceptor(TokenStoreCache tokenStoreCache) {
        this.tokenStoreCache = tokenStoreCache;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletRequest.getServletRequest();

            // 获取查询参数
            String accessToken = httpRequest.getParameter("access_token");
            UserDetail user = tokenStoreCache.getUser(accessToken);
            if (user != null) {
                // 将参数存储在attributes中
                attributes.put("userId", user.getId());
                attributes.put("userName", user.getUsername());
            }
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
