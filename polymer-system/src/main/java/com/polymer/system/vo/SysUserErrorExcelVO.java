package com.polymer.system.vo;

import com.polymer.framework.common.annotation.Excel;

/**
 * 用户导入错误数据VO
 * 继承导入VO，增加错误原因字段
 */
public class SysUserErrorExcelVO extends SysUserExcelVO {

    @Excel(name = "错误原因", cellType = Excel.ColumnType.STRING)
    private String errorReason;

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
