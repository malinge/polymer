package com.polymer.system.entity;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 导入导出记录表对象 sys_import_export_record
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2026-06-29
 */
public class SysImportExportRecordEntity extends BaseEntity  {
    private static final long serialVersionUID = 1L;
    /**
    * 操作人姓名
    */
	private String operatorName;
    /**
    * 业务对象类型：user-用户, dept-部门, role-角色等
    */
	private String businessType;
    /**
    * 操作类型：import-导入, export-导出
    */
	private String operationType;
    /**
    * 总数据量
    */
	private Integer totalCount;
    /**
    * 成功量
    */
	private Integer successCount;
    /**
    * 失败量
    */
	private Integer errorCount;
    /**
    * 冲突处理数量（根据import_strategy判断：skip-跳过数量，override-覆盖数量）
    */
	private Integer conflictHandleCount;
    /**
    * 导入策略：update-更新, skip-跳过, override-覆盖
    */
	private String importStrategy;
    /**
    * 错误数据文件地址（相对路径）
    */
	private String errorFileUrl;
    /**
    * 结果文件地址（相对路径）
    */
	private String resultFileUrl;
    /**
    * 备注/错误摘要
    */
	private String remark;
    /**
    * 部门ID
    */
	private Long deptId;

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
    public Integer getTotalCount() {
    return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public Integer getSuccessCount() {
    return successCount;
    }
    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }
    public Integer getErrorCount() {
    return errorCount;
    }
    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
    public Integer getConflictHandleCount() {
    return conflictHandleCount;
    }
    public void setConflictHandleCount(Integer conflictHandleCount) {
        this.conflictHandleCount = conflictHandleCount;
    }
    public String getImportStrategy() {
    return importStrategy;
    }
    public void setImportStrategy(String importStrategy) {
        this.importStrategy = importStrategy;
    }
    public String getErrorFileUrl() {
    return errorFileUrl;
    }
    public void setErrorFileUrl(String errorFileUrl) {
        this.errorFileUrl = errorFileUrl;
    }
    public String getResultFileUrl() {
    return resultFileUrl;
    }
    public void setResultFileUrl(String resultFileUrl) {
        this.resultFileUrl = resultFileUrl;
    }
    public String getRemark() {
    return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Long getDeptId() {
    return deptId;
    }
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("operatorName", getOperatorName())
            .append("businessType", getBusinessType())
            .append("operationType", getOperationType())
            .append("totalCount", getTotalCount())
            .append("successCount", getSuccessCount())
            .append("errorCount", getErrorCount())
            .append("conflictHandleCount", getConflictHandleCount())
            .append("importStrategy", getImportStrategy())
            .append("errorFileUrl", getErrorFileUrl())
            .append("resultFileUrl", getResultFileUrl())
            .append("remark", getRemark())
            .append("deptId", getDeptId())
            .toString();
    }
}