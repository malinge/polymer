package com.polymer.framework.web.xss.core;

import com.polymer.framework.common.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * XSS Request Wrapper
 *
 * @author polymer
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 如果是json数据，则不处理
        if (!StringUtils.startsWithIgnoreCase(this.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            return super.getInputStream();
        }

        // 读取内容，进行xss过滤
        String content = IOUtils.toString(super.getInputStream(), StandardCharsets.UTF_8);
        content = filterXss(content);

        // 返回新的 ServletInputStream
        final ByteArrayInputStream bis = new ByteArrayInputStream(StringUtils.getBytes(content, StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return bis.read();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);

        return filterXss(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = filterXss(parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = filterXss(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return filterXss(value);
    }

    private String filterXss(String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }

        return XssUtils.filter(content);
    }

}