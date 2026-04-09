package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 用户角色关系
 *
 * @author polymer
 */
public class SysUserRoleEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //角色ID
    private Long roleId;

    //用户ID
    private Long userId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
