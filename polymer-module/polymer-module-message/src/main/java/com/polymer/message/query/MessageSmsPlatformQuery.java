package com.polymer.message.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 短信平台查询
 *
 * @author polymer
 */
@Schema(description = "短信平台查询")
public class MessageSmsPlatformQuery extends PageParam {
    @Schema(description = "平台类型  0：阿里云   1：腾讯云")
    private Integer platform;

    @Schema(description = "短信签名")
    private String signName;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}