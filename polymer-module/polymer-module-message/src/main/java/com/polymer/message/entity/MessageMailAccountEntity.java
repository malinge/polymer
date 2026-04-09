package com.polymer.message.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 邮箱账号表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
public class MessageMailAccountEntity extends BaseEntity {

    //邮箱
    private String mail;

    //用户名
    private String username;

    //密码
    private String password;

    //SMTP 服务器域名
    private String host;

    //SMTP 服务器端口
    private Integer port;

    //协议
    private String protocol;

    //是否开启 SSL
    private Boolean sslEnable;

    //邮箱
    private Long deptId;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Boolean getSslEnable() {
        return sslEnable;
    }

    public void setSslEnable(Boolean sslEnable) {
        this.sslEnable = sslEnable;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}