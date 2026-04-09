package com.polymer.generator.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码生成表对象 gen_table
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2025-10-21
 */
public class TableEntity implements Serializable  {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 类名
     */
    private String className;
    /**
     * 说明
     */
    private String tableComment;
    /**
     * 作者
     */
    private String author;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 项目包名
     */
    private String packageName;
    /**
     * 项目版本号
     */
    private String version;
    /**
     * 生成方式  0：zip压缩包   1：自定义目录
     */
    private Integer generatorType;
    /**
     * 后端生成路径
     */
    private String backendPath;
    /**
     * 前端生成路径
     */
    private String frontendPath;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 功能名
     */
    private String functionName;
    /**
     * 表单布局  1：一列   2：两列
     */
    private Integer formLayout;
    /**
     * 数据源ID
     */
    private Long datasourceId;
    /**
     * 基类ID
     */
    private Long baseclassId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 字段列表
     */
    private List<TableFieldEntity> fieldList;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getTableComment() {
        return tableComment;
    }
    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public Integer getGeneratorType() {
        return generatorType;
    }
    public void setGeneratorType(Integer generatorType) {
        this.generatorType = generatorType;
    }
    public String getBackendPath() {
        return backendPath;
    }
    public void setBackendPath(String backendPath) {
        this.backendPath = backendPath;
    }
    public String getFrontendPath() {
        return frontendPath;
    }
    public void setFrontendPath(String frontendPath) {
        this.frontendPath = frontendPath;
    }
    public String getModuleName() {
        return moduleName;
    }
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getFunctionName() {
        return functionName;
    }
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    public Integer getFormLayout() {
        return formLayout;
    }
    public void setFormLayout(Integer formLayout) {
        this.formLayout = formLayout;
    }
    public Long getDatasourceId() {
        return datasourceId;
    }
    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }
    public Long getBaseclassId() {
        return baseclassId;
    }
    public void setBaseclassId(Long baseclassId) {
        this.baseclassId = baseclassId;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<TableFieldEntity> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<TableFieldEntity> fieldList) {
        this.fieldList = fieldList;
    }
}
