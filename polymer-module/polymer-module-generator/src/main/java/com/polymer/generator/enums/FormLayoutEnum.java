package com.polymer.generator.enums;

/**
 * 表单布局 枚举
 * polymer@126.com
 *
 */
public enum FormLayoutEnum {
    ONE(1),
    TWO(2);

    FormLayoutEnum(Integer value) {
        this.value = value;
    }

    private final Integer value;

    public Integer getValue() {
        return value;
    }
}
