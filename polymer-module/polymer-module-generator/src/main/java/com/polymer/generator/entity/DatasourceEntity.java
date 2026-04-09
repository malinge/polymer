package com.polymer.generator.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据源管理对象 gen_datasource
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2025-10-21
 */
public class DatasourceEntity implements Serializable  {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 连接名
     */
    private String connName;
    /**
     * URL
     */
    private String connUrl;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
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
    public String getDbType() {
        return dbType;
    }
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
    public String getConnName() {
        return connName;
    }
    public void setConnName(String connName) {
        this.connName = connName;
    }
    public String getConnUrl() {
        return connUrl;
    }
    public void setConnUrl(String connUrl) {
        this.connUrl = connUrl;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}