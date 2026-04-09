package com.polymer.framework.logger.enums;

/**
 * 操作类型
 *
 * @author polymer
 */
public enum OperateTypeEnum {
    /**
     * 查询
     */
    GET(1),
    /**
     * 新增
     */
    INSERT(2),
    /**
     * 修改
     */
    UPDATE(3),
    /**
     * 删除
     */
    DELETE(4),
    /**
     * 导出
     */
    EXPORT(5),
    /**
     * 导入
     */
    IMPORT(6),
    /**
     * 其它
     */
    OTHER(0);

    private final int value;

    public int getValue() {
        return value;
    }

    OperateTypeEnum(int value) {
        this.value = value;
    }
}
