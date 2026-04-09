package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 附件管理查询
 *
 * @author polymer
 */
@Schema(description = "附件管理查询")
public class SysAttachmentQuery extends PageParam {
    @Schema(description = "附件名称")
    private String name;

    @Schema(description = "存储平台")
    private String platform;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}