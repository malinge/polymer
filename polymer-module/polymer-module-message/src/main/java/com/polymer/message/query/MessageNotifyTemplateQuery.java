package com.polymer.message.query;

import com.polymer.framework.common.pojo.PageParam;
import com.polymer.framework.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 站内信模板查询
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
@Schema(description = "站内信模板查询")
public class MessageNotifyTemplateQuery extends PageParam {
    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "模版编码")
    private String code;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime[] createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime[] getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime[] createTime) {
        this.createTime = createTime;
    }
}