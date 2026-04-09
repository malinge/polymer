package com.polymer.framework.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询公共参数
 *
 * @author polymer
 */
public class PageParam implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    /**
     * 每页条数 - 不分页
     * 例如说，导出接口，可以设置 {@link #pageSize} 为 0 不分页，查询所有数据。
     */
    public static final Integer PAGE_SIZE_NONE = 0;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @Schema(description = "当前页码", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer pageNo = PAGE_NO;

    @NotNull(message = "每页条数不能为空")
    @Range(min = 1, max = 1000, message = "每页条数，取值范围 1-1000")
    @Schema(description = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer pageSize = PAGE_SIZE;

    String dataScope;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }
}
