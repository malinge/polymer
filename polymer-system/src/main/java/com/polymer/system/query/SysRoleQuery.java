package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 角色管理查询
 *
 * @author polymer
 */
@Schema(description = "角色查询")
public class SysRoleQuery extends PageParam {
    @Schema(description = "角色名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
