package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 附件管理
 *
 * @author polymer
 */
public class SysAttachmentEntity extends BaseEntity {

    //业务标识
    private String bizMark;

    //业务类型
    private String bizType;

    //附件名称
    private String name;

    //附件地址
    private String url;

    //附件大小
    private Long size;

    //附件类型
    private String type;

    //存储平台
    private String platform;

    public String getBizMark() {
        return bizMark;
    }

    public void setBizMark(String bizMark) {
        this.bizMark = bizMark;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}