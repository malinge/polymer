package com.polymer.api.storage.dto;

import java.io.Serializable;

public class SysFileUploadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //文件名称
    private String name;

    //文件地址(相对路径)
    private String url;

    //文件大小
    private Long size;

    //存储平台
    private String platform;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
