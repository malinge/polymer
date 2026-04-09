package com.polymer.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色管理
 *
 * @author polymer
 */
@Schema(description = "角色")
public class SysRoleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @Schema(description = "角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "数据范围  0：全部数据  1：本部门及子部门数据  2：本部门数据  3：本人数据  4：自定义数据")
    private Integer dataScope;

    @Schema(description = "菜单ID列表")
    private List<Long> menuIdList;

    @Schema(description = "部门ID列表")
    private List<Long> deptIdList;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}