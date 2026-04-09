package com.polymer.generator.common.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

/**
 * 参数加解密注解
 *
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface EncryptParameter {
}
