package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

import java.util.List;

/**
 * 角色数据权限
 *
 * @author polymer
 */
public class SysRoleDataScopeEntity extends BaseEntity {

    //角色ID
    private Long roleId;

    //部门ID
    private Long deptId;

    //dataScope
    private Integer dataScope;

    //deptIdList
    private List<Long> deptIdList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getDataScope() {
        return dataScope;
    }

    public void setDataScope(Integer dataScope) {
        this.dataScope = dataScope;
    }

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }
}