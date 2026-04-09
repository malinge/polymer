package com.polymer.framework.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.polymer.framework.common.utils.DateUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class JacksonConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class,
                    new LocalDateTimeSerializer(DateUtils.createFormatter(DateUtils.YYYY_MM_DD_HH_MM_SS)));
            builder.serializerByType(LocalDate.class,
                    new LocalDateSerializer(DateUtils.createFormatter(DateUtils.YYYY_MM_DD)));
            builder.serializerByType(LocalTime.class,
                    new LocalTimeSerializer(DateUtils.createFormatter(DateUtils.HH_MM_SS)));

            builder.deserializerByType(LocalDateTime.class,
                    new LocalDateTimeDeserializer(DateUtils.createFormatter(DateUtils.YYYY_MM_DD_HH_MM_SS)));
            builder.deserializerByType(LocalDate.class,
                    new LocalDateDeserializer(DateUtils.createFormatter(DateUtils.YYYY_MM_DD)));
            builder.deserializerByType(LocalTime.class,
                    new LocalTimeDeserializer(DateUtils.createFormatter(DateUtils.HH_MM_SS)));

            // 序列化时包含所有字段（即使为null）
            builder.serializationInclusion(JsonInclude.Include.ALWAYS);//接口返回全部字段
            // 设置 Jackson 在反序列化 JSON 字符串到 Java 对象时，如果遇到 JSON 中存在但 Java 对象中不存在的属性（未知属性），是否失败（抛出异常）
            // `false` 表示忽略未知属性，不会抛出异常 Jackson 默认是 `true`
            builder.failOnUnknownProperties(false);
            // 设置java.util.Date时间类的序列化以及反序列化的格式
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 禁用将日期序列化为时间戳的功能，使用格式化字符串
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }
}
