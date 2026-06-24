package com.polymer.demo.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 多文件上传样例对象 demo_multiple_files
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2026-06-23
 */
public class DemoMultipleFilesEntity extends BaseEntity  {
    private static final long serialVersionUID = 1L;
    /**
    * 名称
    */
	private String name;
    /**
     * 描述
     */
	private String description;
    /**
    * 部门ID
    */
	private Long deptId;

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

    public Long getDeptId() {
    return deptId;
    }
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("deptId", getDeptId())
            .append("description", getDescription())
            .toString();
    }
}
