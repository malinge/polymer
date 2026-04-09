package com.polymer.storage.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 预签名地址
 *
 * @author polymer
 */
@Schema(description = "预签名地址")
public class SysClientFilePresignedVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "文件路径，包含文件名")
    private String path;

    @Schema(description = "存储平台")
    private String platform;

    @Schema(description = "预签名地址")
    private String presignedUrl;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPresignedUrl() {
        return presignedUrl;
    }

    public void setPresignedUrl(String presignedUrl) {
        this.presignedUrl = presignedUrl;
    }
}
