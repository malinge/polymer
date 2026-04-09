package com.polymer.quartz.enums;

/**
 * 定时任务并发枚举
 *
 * @author polymer
 */
public enum ScheduleConcurrentEnum {
    /**
     * 禁止
     */
    NO(0),
    /**
     * 允许
     */
    YES(1);

    private final int value;

    public int getValue() {
        return value;
    }

    ScheduleConcurrentEnum(int value) {
        this.value = value;
    }
}
