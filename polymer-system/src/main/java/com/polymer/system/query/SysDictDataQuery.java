package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

/**
 * 字典数据
 *
 * @author polymer
 */
@Schema(description = "字典数据查询")
public class SysDictDataQuery extends PageParam {
    @Schema(description = "字典类型ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    public Long getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
    }
}
