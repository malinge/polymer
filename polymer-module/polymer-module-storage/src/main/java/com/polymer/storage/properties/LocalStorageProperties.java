package com.polymer.storage.properties;

/**
 * 本地存储配置项
 *
 * @author polymer
 */
public class LocalStorageProperties {

    /**
     * 本地存储路径
     */
    private String path;
    /**
     * 资源起始路径
     */
    private String url = "upload";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
