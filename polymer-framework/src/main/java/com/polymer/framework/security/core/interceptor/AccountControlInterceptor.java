package com.polymer.framework.security.core.interceptor;

import com.polymer.framework.common.exception.ErrorCode;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.JsonUtils;
import com.polymer.framework.security.core.cache.TokenStoreCache;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.framework.security.core.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账号控制拦截器(针对开启同一用户异地登录踢人功能)
 *
 * @author polymer
 */
@Component
public class AccountControlInterceptor implements HandlerInterceptor {
    @Resource
    private TokenStoreCache tokenStoreCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是响应方法，静态资源直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // token
        String accessToken = TokenUtils.getAccessToken(request);
        if (tokenStoreCache.getKickOut() && StringUtils.isNotBlank(accessToken)) {
            // 获取登录用户信息
            UserDetail user = tokenStoreCache.getUser(accessToken);
            if (user != null) {
                // 设置响应格式
                response.setContentType("application/json;charset=UTF-8");
                String accessTokenBefore = tokenStoreCache.getAccessToken(user.getId());
                if (!accessToken.equals(accessTokenBefore)) {
                    response.getWriter().print(JsonUtils.toJsonString(Result.error(ErrorCode.UNAUTHORIZED_ERROR)));
                    return false;
                }
            }
        }

        return true;
    }

}


