package com.polymer.message.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 邮件日志表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Schema(description = "邮件日志表")
public class MessageMailLogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "接收邮箱地址")
    private String toMail;

    @Schema(description = "邮箱账号编号")
    private Long accountId;

    @Schema(description = "发送邮箱地址")
    private String fromMail;

    @Schema(description = "模板编号")
    private Long templateId;

    @Schema(description = "模板编码")
    private String templateCode;

    @Schema(description = "模版发送人名称")
    private String templateNickname;

    @Schema(description = "邮件标题")
    private String templateTitle;

    @Schema(description = "邮件内容")
    private String templateContent;

    @Schema(description = "邮件参数")
    private Map<String, Object> templateParams;

    @Schema(description = "发送状态")
    private Integer sendStatus;

    @Schema(description = "发送时间")

    private LocalDateTime sendTime;

    @Schema(description = "发送返回的消息 ID")
    private String sendMessageId;

    @Schema(description = "发送异常")
    private String sendException;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "创建者")
    private Long creator;

    @Schema(description = "创建时间")

    private LocalDateTime createTime;

    @Schema(description = "更新者")
    private Long updater;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

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

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}