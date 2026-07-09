package com.polymer.demo.vo;

import com.polymer.framework.common.annotation.Excel;

import java.io.Serializable;

/**
* 多文件上传样例
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-23
*/
public class DemoMultipleFilesExcelVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@Excel(name = "名称", required = true)
	private String name;

	@Excel(name = "描述", required = true)
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
