package com.polymer.framework.encrypt.core.filter;

import com.polymer.framework.common.exception.ErrorCode;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.HttpContextUtils;
import com.polymer.framework.common.utils.SpringUtils;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.encrypt.core.annotation.ApiEncrypt;
import com.polymer.framework.encrypt.core.properties.ApiDecryptProperties;
import com.polymer.framework.web.exception.GlobalExceptionHandler;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Crypto 过滤器
 *
 * @author polymer
 */
public class CryptoFilter implements Filter {
    private final ApiDecryptProperties properties;
    private final GlobalExceptionHandler globalExceptionHandler;

    public CryptoFilter(ApiDecryptProperties properties, GlobalExceptionHandler globalExceptionHandler) {
        this.properties = properties;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        // 获取加密注解
        ApiEncrypt apiEncrypt = this.getApiEncryptAnnotation(servletRequest, servletResponse);
        boolean responseFlag = apiEncrypt != null && apiEncrypt.response();
        ServletRequest requestWrapper = null;
        ServletResponse responseWrapper = null;
        EncryptResponseBodyWrapper responseBodyWrapper = null;

        // 是否为 put 或者 post 请求
        if (HttpMethod.PUT.matches(servletRequest.getMethod()) || HttpMethod.POST.matches(servletRequest.getMethod())) {
            // 是否存在加密标头
            String headerValue = servletRequest.getHeader(properties.getHeaderFlag());
            if (StringUtils.isNotBlank(headerValue)) {
                // 请求解密
                requestWrapper = new DecryptRequestBodyWrapper(servletRequest, properties.getPrivateKey(), properties.getHeaderFlag());
            } else {
                // 是否有注解，有就报错，没有放行
                if (StringUtils.isNotNull(apiEncrypt)) {
                    HandlerExceptionResolver exceptionResolver = SpringUtils.getBean("handlerExceptionResolver", HandlerExceptionResolver.class);
                    exceptionResolver.resolveException(
                            servletRequest, servletResponse, null,
                            new ServiceException(ErrorCode.FORBIDDEN));
                    return;
                }
            }
        }

        // 判断是否响应加密
        if (responseFlag) {
            responseBodyWrapper = new EncryptResponseBodyWrapper(servletResponse);
            responseWrapper = responseBodyWrapper;
        }

        chain.doFilter(
                StringUtils.defaultIfNull(requestWrapper, request),
                StringUtils.defaultIfNull(responseWrapper, response));

        if (responseFlag) {
            servletResponse.reset();
            // 对原始内容加密
            String encryptContent = responseBodyWrapper.getEncryptContent(
                    servletResponse, properties.getPublicKey(), properties.getHeaderFlag());
            // 对加密后的内容写出
            servletResponse.getWriter().write(encryptContent);
        }
    }

    /**
     * 获取 ApiEncrypt 注解
     */
    private ApiEncrypt getApiEncryptAnnotation(HttpServletRequest request, HttpServletResponse response) {
        RequestMappingHandlerMapping handlerMapping = SpringUtils.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        // 获取注解
        try {
            HandlerExecutionChain mappingHandler = handlerMapping.getHandler(request);
            if (mappingHandler != null) {
                Object handler = mappingHandler.getHandler();
                // 从handler获取注解
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    return handlerMethod.getMethodAnnotation(ApiEncrypt.class);
                }
            }
        } catch (Throwable ex) {
            Result<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
            HttpContextUtils.writeJSON(response, result);
        }
        return null;

    }

    @Override
    public void destroy() {
    }
}
