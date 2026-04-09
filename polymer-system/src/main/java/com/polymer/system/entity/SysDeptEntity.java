package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 部门管理
 *
 * @author polymer
 */
public class SysDeptEntity extends BaseEntity {

    //上级ID
    private Long pid;

    //部门名称
    private String name;

    // 类型（1：公司；2：子公司；3：部门；4：小组）
    private Integer type;

    //负责人ID
    private Long leaderId;

    // 负责人名称
    private String leaderName;

    //排序
    private Integer sort;

    //部门路径（用于查询子类)
    private String deptPath;

    //上级名称
    private String parentName;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}