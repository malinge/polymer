package com.polymer.message.query;

import com.polymer.framework.common.pojo.PageParam;
import com.polymer.framework.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 站内信消息查询
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
@Schema(description = "站内信消息查询")
public class MessageNotifyMessageQuery extends PageParam {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "模板编码")
    private String templateCode;

    @Schema(description = "模版类型")
    private Integer type;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime[] createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime[] getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime[] createTime) {
        this.createTime = createTime;
    }
}