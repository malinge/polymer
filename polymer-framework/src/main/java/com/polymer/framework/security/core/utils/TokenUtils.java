package com.polymer.framework.security.core.utils;

import com.polymer.framework.common.utils.HttpContextUtils;
import com.polymer.framework.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Token 工具类
 *
 * @author polymer
 */
public class TokenUtils {

    /**
     * 生成 AccessToken
     */
    public static String generator() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取 AccessToken
     */
    public static String getAccessToken() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return getAccessToken(request);
    }

    /**
     * 获取 AccessToken
     */
    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getParameter("access_token");
        }

        return accessToken;
    }
}
