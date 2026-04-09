package com.polymer.system.enums;

/**
 * 字典数据来源
 *
 * @author polymer
 */
public enum DictSourceEnum {
    /**
     * 字典数据
     */
    DICT(0),
    /**
     * 动态SQL
     */
    SQL(1);

    private final int value;

    public int getValue() {
        return value;
    }

    DictSourceEnum(int value) {
        this.value = value;
    }
}
