package com.polymer.message.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件模版表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Schema(description = "邮件模版表")
public class MessageMailTemplateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "模板编码")
    private String code;

    @Schema(description = "邮箱账号")
    private Long accountId;

    @Schema(description = "发送人名称")
    private String nickname;

    @Schema(description = "主题")
    private String subject;

    @Schema(description = "模板标题")
    private String title;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "参数数组")
    private List<String> params;

    @Schema(description = "开启状态")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

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

    @Schema(description = "邮箱账号")
    private String mail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}