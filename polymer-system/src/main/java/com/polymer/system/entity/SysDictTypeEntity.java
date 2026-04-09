package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 字典类型
 *
 * @author polymer
 */
public class SysDictTypeEntity extends BaseEntity {

    //字典类型
    private String dictType;

    //字典名称
    private String dictName;

    //备注
    private String remark;

    //排序
    private Integer sort;

    //来源  0：字典数据  1：动态SQL
    private Integer dictSource;

    //动态sql
    private String dictSql;

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDictSource() {
        return dictSource;
    }

    public void setDictSource(Integer dictSource) {
        this.dictSource = dictSource;
    }

    public String getDictSql() {
        return dictSql;
    }

    public void setDictSql(String dictSql) {
        this.dictSql = dictSql;
    }
}