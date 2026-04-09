package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

import java.util.List;

/**
 * 角色
 *
 * @author polymer
 */
public class SysRoleEntity extends BaseEntity {

    //角色名称
    private String name;

    //角色编码
    private String roleCode;

    //备注
    private String remark;

    //数据范围
    private Integer dataScope;

    //部门ID
    private Long deptId;

    //菜单ID列表
    private List<Long> menuIdList;

    //部门ID列表
    private List<Long> deptIdList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDataScope() {
        return dataScope;
    }

    public void setDataScope(Integer dataScope) {
        this.dataScope = dataScope;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }
}
