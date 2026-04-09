package com.polymer.framework.mybatis.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.polymer.framework.common.constant.Constant.DEFAULT_ALIAS;
import static com.polymer.framework.common.constant.Constant.DEFAULT_DATA_FIELD;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.framework.mybatis.core.annotation
 * CreateTime: 2024-05-24  15:27
 * Description: 数据权限
 *
 * @author polymer
 * @version 2.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
    /**
     * 表别名设置
     */
    String alias() default DEFAULT_ALIAS;

    /**
     * 数据权限表字段名
     */
    String dataId() default DEFAULT_DATA_FIELD;
}
