package com.polymer.framework.common.utils;

import com.polymer.framework.common.core.text.CharsetKit;
import com.polymer.framework.common.exception.UtilException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Http
 *
 * @author polymer
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }


    public static HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getResponse();
    }

    /**
     * 返回 JSON 字符串
     *
     * @param response 响应
     * @param object   对象，会序列化成 JSON 字符串
     */
    @SuppressWarnings("deprecation")
    public static void writeJSON(HttpServletResponse response, Object object) {
        String content = JsonUtils.toJsonString(object);
        // 必须使用 APPLICATION_JSON_UTF8_VALUE，否则会乱码
        write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    public static Map<String, String> getParamMap(HttpServletRequest request) {
        return getParamMapMake(request);
    }

    public static String getBody(HttpServletRequest request) {
        // 只有在 json 请求在读取，因为只有 CacheRequestBodyFilter 才会进行缓存，支持重复读取
        if (isJsonRequest(request)) {
            return getBodyMake(request);
        }
        return null;
    }

    public static boolean isJsonRequest(ServletRequest request) {
        return StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * @param request 请求
     * @return ua
     */
    public static String getUserAgent(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return ua != null ? ua : "";
    }

    public static String getClientIP(HttpServletRequest request) {
        return IpUtils.getIpAddr(request);
    }

    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        AssertUtils.isNull(request, "HttpServletRequest");
        return request.getHeader(HttpHeaders.ORIGIN);
    }

    /**
     * 返回数据给客户端
     *
     * @param response    响应对象{@link HttpServletResponse}
     * @param text        返回的内容
     * @param contentType 返回的类型
     */
    private static void write(HttpServletResponse response, String text, String contentType) {
        response.setContentType(contentType);
        Writer writer = null;
        try {
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            throw new UtilException(e);
        } finally {
            try {
                IOUtils.close(writer);
            } catch (IOException e) {
                // 静默处理
            }
        }
    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    private static Map<String, String> getParamMapMake(ServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), StringUtils.join(entry.getValue(), StringUtils.COMMA));
        }
        return params;
    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    private static Map<String, String[]> getParams(ServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * 获取请求体<br>
     * 调用该方法后，getParam方法将失效
     *
     * @param request {@link ServletRequest}
     * @return 获得请求体
     * @since 4.0.2
     */
    public static String getBodyMake(ServletRequest request) {
        try (final BufferedReader reader = request.getReader()) {
            return IOUtils.toString(reader);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    public static String mapToQueryString(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            if (sb.length() > 0) sb.append("&");
            try {
                sb.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), CharsetKit.UTF_8));
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
        return sb.toString();
    }
}