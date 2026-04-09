package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 字典类型查询
 *
 * @author polymer
 */
@Schema(description = "字典类型查询")
public class SysDictTypeQuery extends PageParam {
    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典名称")
    private String dictName;

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
}
