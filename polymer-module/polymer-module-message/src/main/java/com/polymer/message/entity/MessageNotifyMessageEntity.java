package com.polymer.message.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * 站内信消息
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
public class MessageNotifyMessageEntity extends BaseEntity {

    //用户id
    private Long userId;

    //用户类型
    private Integer userType;

    //模板编码
    private String templateCode;

    //标题
    private String title;

    //内容
    private String content;

    //发送人
    private String sender;

    // 发送人头像
    private String senderAvatar;

    //类型
    private Integer type;

    //是否已读
    private Boolean readStatus;

    //阅读时间
    private LocalDateTime readTime;

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

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public LocalDateTime getReadTime() {
        return readTime;
    }

    public void setReadTime(LocalDateTime readTime) {
        this.readTime = readTime;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}