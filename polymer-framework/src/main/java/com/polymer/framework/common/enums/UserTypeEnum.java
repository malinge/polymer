package com.polymer.framework.common.enums;

import com.polymer.framework.common.core.IntArrayValuable;

import java.util.Arrays;

/**
 * 全局用户类型枚举
 */
public enum UserTypeEnum implements IntArrayValuable {

    MEMBER(1, "会员"), // 面向 c 端，普通用户
    ADMIN(2, "管理员"); // 面向 b 端，管理后台

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserTypeEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    UserTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
