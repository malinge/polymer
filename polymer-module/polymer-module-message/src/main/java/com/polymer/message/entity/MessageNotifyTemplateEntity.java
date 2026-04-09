package com.polymer.message.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

import java.util.List;

/**
 * 站内信模板
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
public class MessageNotifyTemplateEntity extends BaseEntity {

    //模板名称
    private String name;

    //模版编码
    private String code;

    //发送人名称
    private String nickname;

    //模版内容
    private String content;

    //类型
    private Integer type;

    //参数数组
    private List<String> params;

    //状态
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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