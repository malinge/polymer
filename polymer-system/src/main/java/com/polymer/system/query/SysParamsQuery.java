package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 参数管理查询
 *
 * @author polymer
 */
@Schema(description = "参数管理查询")
public class SysParamsQuery extends PageParam {
    @Schema(description = "系统参数")
    private Integer paramType;

    @Schema(description = "参数键")
    private String paramKey;

    @Schema(description = "参数值")
    private String paramValue;

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}