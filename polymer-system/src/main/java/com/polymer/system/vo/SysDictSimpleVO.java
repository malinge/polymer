package com.polymer.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "字典类型简版")
public class SysDictSimpleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典类型编号")
    private Long id;

    @Schema(description = "字典类型名称")
    private String dictName;

    @Schema(description = "字典类型")
    private String dictType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
}
