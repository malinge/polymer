package com.polymer.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * app信息表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2025-05-09
 */
@Schema(description = "app信息表")
public class SysAppDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Integer id;

    private String appName;

    private String appId;

    private String appSecret;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("appName", getAppName())
                .append("appId", getAppId())
                .append("appSecret", getAppSecret())
                .toString();
    }
}