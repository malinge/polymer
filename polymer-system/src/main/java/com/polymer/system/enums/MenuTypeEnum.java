package com.polymer.system.enums;

/**
 * 菜单类型枚举
 *
 * @author polymer
 */
public enum MenuTypeEnum {
    /**
     * 菜单
     */
    MENU(0),
    /**
     * 按钮
     */
    BUTTON(1),
    /**
     * 接口
     */
    INTERFACE(2);

    private final int value;

    public int getValue() {
        return value;
    }

    MenuTypeEnum(int value) {
        this.value = value;
    }
}
