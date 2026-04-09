package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 菜单管理
 *
 * @author polymer
 */
public class SysMenuEntity extends BaseEntity {

    //上级ID
    private Long pid;

    //菜单名称
    private String name;

    //菜单URL
    private String url;

    //授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
    private String authority;

    //类型   0：菜单   1：按钮   2：接口
    private Integer type;

    //打开方式   0：内部   1：外部
    private Integer openStyle;

    //菜单图标
    private String icon;

    //排序
    private Integer sort;

    //parentName
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOpenStyle() {
        return openStyle;
    }

    public void setOpenStyle(Integer openStyle) {
        this.openStyle = openStyle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}