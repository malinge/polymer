package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 操作日志查询
 *
 * @author polymer
 */
@Schema(description = "操作日志查询")
public class SysLogOperateQuery extends PageParam {
    @Schema(description = "用户")
    private String realName;

    @Schema(description = "模块名")
    private String module;

    @Schema(description = "请求URI")
    private String reqUri;

    @Schema(description = "操作状态")
    private Integer status;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}