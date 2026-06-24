package com.polymer.demo.vo;

import com.polymer.api.system.dto.SysAttachmentDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
* 多文件上传样例
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-23
*/
@Schema(description = "多文件上传样例")
public class DemoMultipleFilesVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "名称")
	private String name;

	@Schema(description = "描述")
	private String description;

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

	@Schema(description = "图片")
	private List<SysAttachmentDTO> images;

	@Schema(description = "附件")
	private List<SysAttachmentDTO> attachments;



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<SysAttachmentDTO> getImages() {
		return images;
	}

	public void setImages(List<SysAttachmentDTO> images) {
		this.images = images;
	}

	public List<SysAttachmentDTO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<SysAttachmentDTO> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("name", getName())
				.append("description", getDescription())
			.append("deptId", getDeptId())
			.append("creator", getCreator())
			.append("createTime", getCreateTime())
			.append("updater", getUpdater())
			.append("updateTime", getUpdateTime())
			.toString();
	}
}
