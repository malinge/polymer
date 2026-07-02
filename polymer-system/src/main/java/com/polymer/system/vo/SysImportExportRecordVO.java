package com.polymer.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 导入导出记录表
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-29
*/
@Schema(description = "导入导出记录表")
public class SysImportExportRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键ID")
	private Long id;

	@Schema(description = "操作人姓名")
	private String operatorName;

	@Schema(description = "业务对象类型：user-用户, dept-部门, role-角色等")
	private String businessType;

	@Schema(description = "操作类型：import-导入, export-导出")
	private String operationType;

	@Schema(description = "总数据量")
	private Integer totalCount;

	@Schema(description = "成功量")
	private Integer successCount;

	@Schema(description = "失败量")
	private Integer errorCount;

	@Schema(description = "冲突处理数量（根据import_strategy判断：skip-跳过数量，override-覆盖数量）")
	private Integer conflictHandleCount;

	@Schema(description = "导入策略：update-更新, skip-跳过, override-覆盖")
	private String importStrategy;

	@Schema(description = "错误数据文件地址（相对路径）")
	private String errorFileUrl;

	@Schema(description = "结果文件地址（相对路径）")
	private String resultFileUrl;

	@Schema(description = "备注/错误摘要")
	private String remark;

	@Schema(description = "部门ID")
	private Long deptId;

	@Schema(description = "创建者")
	private Long creator;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新者")
	private Long updater;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public Long getUpdater() {
		return updater;
	}
	public void setUpdater(Long updater) {
		this.updater = updater;
	}
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
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
			.append("creator", getCreator())
			.append("createTime", getCreateTime())
			.append("updater", getUpdater())
			.append("updateTime", getUpdateTime())
			.toString();
	}
}