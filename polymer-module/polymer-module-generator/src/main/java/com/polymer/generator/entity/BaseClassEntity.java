package com.polymer.generator.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基类管理对象 gen_base_class
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2025-10-21
 */
public class BaseClassEntity implements Serializable  {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 基类包名
     */
    private String packageName;
    /**
     * 基类编码
     */
    private String code;
    /**
     * 基类字段，多个用英文逗号分隔
     */
    private String fields;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getFields() {
        return fields;
    }
    public void setFields(String fields) {
        this.fields = fields;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}