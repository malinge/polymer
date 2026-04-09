package com.polymer.quartz.enums;

/**
 * 定时任务状态枚举
 *
 * @author polymer
 */
public enum ScheduleStatusEnum {
    /**
     * 暂停
     */
    PAUSE(0),
    /**
     * 正常
     */
    NORMAL(1);

    private final int value;

    public int getValue() {
        return value;
    }

    ScheduleStatusEnum(int value) {
        this.value = value;
    }
}
