package com.polymer.demo.vo;

import com.polymer.framework.common.annotation.Excel;

/**
* 多文件上传样例
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-23
*/
public class DemoMultipleFilesErrorExcelVO extends DemoMultipleFilesExcelVO {

	@Excel(name = "错误原因", cellType = Excel.ColumnType.STRING)
	private String errorReason;

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
}
