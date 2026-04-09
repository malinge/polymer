package com.polymer.system.entity;

import java.time.LocalDateTime;

/**
 * 操作日志
 *
 * @author polymer
 */
public class SysLogErrorEntity {
    //id
    private Long id;

    //账号编号
    private Long userId;

    //用户类型
    private Integer userType;

    //请求方法名
    private String requestMethod;

    //访问地址
    private String requestUrl;

    //请求参数
    private String requestParams;

    //用户 IP
    private String userIp;

    //浏览器 UA
    private String userAgent;

    //异常时间
    private LocalDateTime exceptionTime;

    //异常名
    private String exceptionName;

    //异常发生的类全名
    private String exceptionClassName;

    //异常发生的类文件
    private String exceptionFileName;

    //异常发生的方法名
    private String exceptionMethodName;

    //异常发生的方法所在行
    private Integer exceptionLineNumber;

    //异常的栈轨迹异常的栈轨迹
    private String exceptionStackTrace;

    //异常导致的根消息
    private String exceptionRootCauseMessage;

    //异常导致的消息
    private String exceptionMessage;

    //创建时间
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getExceptionTime() {
        return exceptionTime;
    }

    public void setExceptionTime(LocalDateTime exceptionTime) {
        this.exceptionTime = exceptionTime;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }

    public String getExceptionFileName() {
        return exceptionFileName;
    }

    public void setExceptionFileName(String exceptionFileName) {
        this.exceptionFileName = exceptionFileName;
    }

    public String getExceptionMethodName() {
        return exceptionMethodName;
    }

    public void setExceptionMethodName(String exceptionMethodName) {
        this.exceptionMethodName = exceptionMethodName;
    }

    public Integer getExceptionLineNumber() {
        return exceptionLineNumber;
    }

    public void setExceptionLineNumber(Integer exceptionLineNumber) {
        this.exceptionLineNumber = exceptionLineNumber;
    }

    public String getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public void setExceptionStackTrace(String exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
    }

    public String getExceptionRootCauseMessage() {
        return exceptionRootCauseMessage;
    }

    public void setExceptionRootCauseMessage(String exceptionRootCauseMessage) {
        this.exceptionRootCauseMessage = exceptionRootCauseMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}