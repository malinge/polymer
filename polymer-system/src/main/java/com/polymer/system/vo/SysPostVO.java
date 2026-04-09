package com.polymer.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 岗位管理
 *
 * @author polymer
 */
@Schema(description = "岗位管理")
public class SysPostVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "岗位编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "岗位编码不能为空")
    private String postCode;

    @Schema(description = "岗位名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "岗位名称不能为空")
    private String postName;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    @Schema(description = "状态  0：停用   1：正常", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 1, message = "状态不正确")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}