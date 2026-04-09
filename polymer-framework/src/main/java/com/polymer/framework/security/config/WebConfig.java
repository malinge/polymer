package com.polymer.framework.security.config;

import com.polymer.framework.common.utils.DateUtils;
import com.polymer.framework.security.core.interceptor.AccountControlInterceptor;
import com.polymer.framework.security.core.interceptor.SignatureInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.security.config
 * CreateTime: 2023-11-30  09:17
 * Description: WebConfig
 *
 * @author polymer
 * @version 2.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private AccountControlInterceptor accountControlInterceptor;
    @Resource
    private SignatureInterceptor signatureInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 执行顺序：accountControlInterceptor -> SignatureInterceptor
        // 账号控制拦截器
        registry.addInterceptor(accountControlInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/sys/auth/logout");

        // 对外暴露接口appid签名认证
        registry.addInterceptor(signatureInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/sys/auth/logout");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 注册全局转换器
        registry.addConverter(new StringToLocalDateTimeConverter());
        registry.addConverter(new StringToLocalDateConverter());
        registry.addConverter(new StringToLocalTimeConverter());
    }

    public static class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            try {
                return LocalDateTime.parse(source, DateUtils.createFormatter(DateUtils.YYYY_MM_DD_HH_MM_SS));
            } catch (DateTimeParseException e) {
                // 可添加日志记录或自定义处理
                throw new IllegalArgumentException("无效的日期时间格式，应为 yyyy-MM-dd HH:mm:ss");
            }
        }
    }

    public static class StringToLocalDateConverter implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(source, DateUtils.createFormatter(DateUtils.YYYY_MM_DD));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("无效的日期格式，应为 yyyy-MM-dd");
            }
        }
    }

    public static class StringToLocalTimeConverter implements Converter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            try {
                return LocalTime.parse(source, DateUtils.createFormatter(DateUtils.HH_MM_SS));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("无效的时间格式，应为 HH:mm:ss");
            }
        }
    }
}