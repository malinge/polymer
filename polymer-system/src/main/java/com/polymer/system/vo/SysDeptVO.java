package com.polymer.system.vo;

import com.polymer.framework.common.pojo.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 部门列表
 *
 * @author polymer
 */
@Schema(description = "部门")
public class SysDeptVO extends TreeNode<SysDeptVO> {

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @Schema(description = "类型（1：公司；2：子公司；3：部门；4：小组）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门类型不能为空")
    private Integer type;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    @Schema(description = "负责人ID")
    private Long leaderId;

    @Schema(description = "负责人名称")
    private String leaderName;

    @Schema(description = "部门路径（用于查询子类）")
    private String deptPath;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "上级名称")
    private String parentName;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}