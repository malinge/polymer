package com.polymer.framework.mybatis.core.enums;

/**
 * 数据范围枚举
 * 本架构采用的优先级排序：ALL > CUSTOM > DEPT_AND_CHILD > DEPT_ONLY > SELF
 * @author polymer
 */
public enum DataScopeEnum {
    /**
     * 全部数据
     */
    ALL(0),
    /**
     * 自定义数据
     */
    CUSTOM(1),
    /**
     * 本部门及子部门数据
     */
    DEPT_AND_CHILD(2),
    /**
     * 本部门数据
     */
    DEPT_ONLY(3),
    /**
     * 本人数据
     */
    SELF(4);

    private final Integer value;

    public Integer getValue() {
        return value;
    }

    DataScopeEnum(Integer value) {
        this.value = value;
    }
}