package com.polymer.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.polymer.framework.common.pojo.PageParam;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;


/**
* 导入导出记录表查询
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-29
*/
@Schema(description = "导入导出记录表查询")
public class SysImportExportRecordQuery extends PageParam {
    private static final long serialVersionUID = 1L;

    private Long creator;

    @Schema(description = "操作人姓名")
    private String operatorName;

    @Schema(description = "业务对象类型：user-用户, dept-部门, role-角色等")
    private String businessType;

    @Schema(description = "操作类型：import-导入, export-导出")
    private String operationType;

    @Schema(description = "开始创建时间")
    private LocalDateTime beginCreateTime;

    @Schema(description = "结束创建时间")
    private LocalDateTime endCreateTime;

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getOperatorName() {
        return operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getBusinessType() {
        return businessType;
    }
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
    public String getOperationType() {
        return operationType;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    public LocalDateTime getBeginCreateTime() {
        return beginCreateTime;
    }
    public void setBeginCreateTime(LocalDateTime beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public LocalDateTime getEndCreateTime() {
        return endCreateTime;
    }
    public void setEndCreateTime(LocalDateTime endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}