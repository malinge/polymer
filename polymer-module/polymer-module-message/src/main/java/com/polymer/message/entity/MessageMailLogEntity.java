package com.polymer.message.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 邮件日志表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
public class MessageMailLogEntity extends BaseEntity {

    //用户id
    private Long userId;

    //用户类型
    private Integer userType;

    //接收邮箱地址
    private String toMail;

    //邮箱账号编号
    private Long accountId;

    //发送邮箱地址
    private String fromMail;

    //模板编号
    private Long templateId;

    //模板编码
    private String templateCode;

    //模版发送人名称
    private String templateNickname;

    //邮件标题
    private String templateTitle;

    //邮件内容
    private String templateContent;

    //邮件参数
    private Map<String, Object> templateParams;

    //发送状态
    private Integer sendStatus;

    //发送时间
    private LocalDateTime sendTime;

    //发送返回的消息 ID
    private String sendMessageId;

    //发送异常
    private String sendException;

    //部门ID
    private Long deptId;

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

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateNickname() {
        return templateNickname;
    }

    public void setTemplateNickname(String templateNickname) {
        this.templateNickname = templateNickname;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public Map<String, Object> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Map<String, Object> templateParams) {
        this.templateParams = templateParams;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendMessageId() {
        return sendMessageId;
    }

    public void setSendMessageId(String sendMessageId) {
        this.sendMessageId = sendMessageId;
    }

    public String getSendException() {
        return sendException;
    }

    public void setSendException(String sendException) {
        this.sendException = sendException;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}