package com.polymer.message.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 邮箱发送消息
 *
 * @author 芋道源码
 */
public class MessageMailSendMessage {

    /**
     * 邮件日志编号
     */
    @NotNull(message = "邮件日志编号不能为空")
    private Long logId;
    /**
     * 接收邮件地址
     */
    @NotNull(message = "接收邮件地址不能为空")
    private String mail;
    /**
     * 邮件账号编号
     */
    @NotNull(message = "邮件账号编号不能为空")
    private Long accountId;

    /**
     * 邮件发件人
     */
    private String nickname;

    /**
     * 主题
     */
    private String subject;
    /**
     * 邮件标题
     */
    @NotEmpty(message = "邮件标题不能为空")
    private String title;
    /**
     * 邮件内容
     */
    @NotEmpty(message = "邮件内容不能为空")
    private String content;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
