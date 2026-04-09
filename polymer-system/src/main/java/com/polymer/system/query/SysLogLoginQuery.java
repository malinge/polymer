package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 登录日志查询
 *
 * @author polymer
 */


@Schema(description = "登录日志查询")
public class SysLogLoginQuery extends PageParam {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录地点")
    private String address;

    @Schema(description = "登录状态  0：失败   1：成功")
    private Integer status;

}