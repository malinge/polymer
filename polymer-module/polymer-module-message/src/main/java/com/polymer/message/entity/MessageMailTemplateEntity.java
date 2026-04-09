package com.polymer.message.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

import java.util.List;

/**
 * 邮件模版表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
public class MessageMailTemplateEntity extends BaseEntity {

    //模板名称
    private String name;

    //模板编码
    private String code;

    //发送的邮箱账号编号
    private Long accountId;

    //发送人名称
    private String nickname;

    //主题
    private String subject;

    //模板标题
    private String title;

    //模板内容
    private String content;

    //参数数组(自动根据内容生成)
    private List<String> params;

    //开启状态
    private Integer status;

    //备注
    private String remark;

    //部门ID
    private Long deptId;

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
}