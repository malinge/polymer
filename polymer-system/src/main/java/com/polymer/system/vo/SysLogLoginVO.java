package com.polymer.system.vo;

import com.polymer.framework.common.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志
 *
 * @author polymer
 */
@Schema(description = "登录日志")
public class SysLogLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;


    @Schema(description = "id")
    private Long id;

    //@Excel(name = "用户名", dictType = "user_gender")
    @Excel(name = "用户名")
    @Schema(description = "用户名")
    private String username;

    @Excel(name = "登录IP")
    @Schema(description = "登录IP")
    private String ip;

    @Excel(name = "登录地点")
    @Schema(description = "登录地点")
    private String address;

    @Excel(name = "User Agent")
    @Schema(description = "User Agent")
    private String userAgent;

    @Excel(name = "登录状态", dictType = "success_fail")
    @Schema(description = "登录状态  0：失败   1：成功")
    private Integer status;

    @Excel(name = "操作信息", dictType = "login_operation")
    @Schema(description = "操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误")
    private Integer operation;

    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}