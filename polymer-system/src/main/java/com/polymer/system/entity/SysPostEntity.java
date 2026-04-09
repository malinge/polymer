package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 岗位管理
 *
 * @author polymer
 */
public class SysPostEntity extends BaseEntity {

    //岗位编码
    private String postCode;

    //岗位名称
    private String postName;

    //排序
    private Integer sort;

    //状态  0：停用   1：正常
    private Integer status;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}