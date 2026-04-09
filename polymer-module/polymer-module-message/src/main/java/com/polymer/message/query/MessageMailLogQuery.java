package com.polymer.message.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 邮件日志表查询
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Schema(description = "邮件日志表查询")
public class MessageMailLogQuery extends PageParam {
    @Schema(description = "邮件标题")
    private String templateTitle;

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }
}