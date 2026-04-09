package com.polymer.message.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 短信日志查询
 *
 * @author polymer
 */
@Schema(description = "短信日志查询")
public class MessageSmsLogQuery extends PageParam {
    @Schema(description = "平台ID")
    private Long platformId;

    @Schema(description = "平台类型")
    private Integer platform;

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
}