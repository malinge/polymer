package com.polymer.system.query;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 分配角色查询
 *
 * @author polymer
 */
@Schema(description = "分配角色查询")
public class SysRoleUserQuery extends SysUserQuery {
    @Schema(description = "角色ID")
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
