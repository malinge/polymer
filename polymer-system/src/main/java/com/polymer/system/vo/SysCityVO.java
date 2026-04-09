package com.polymer.system.vo;

import com.polymer.framework.common.pojo.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
* 城市管理
*
* @author polymer zhangxf@126.com
* @since 1.0.0 2025-07-18
*/
@Schema(description = "城市管理")
public class SysCityVO extends TreeNode<SysCityVO> {
	private static final long serialVersionUID = 1L;

	@Schema(description = "城市名称")
	@NotBlank(message = "城市名称不能为空")
	private String name;

	@Schema(description = "城市拼音")
	private String pinyin;

	@Schema(description = "城市简称")
	private String shortName;

	@Schema(description = "城市全称")
	private String fullName;

	@Schema(description = "行政编码")
	@NotBlank(message = "行政编码不能为空")
	private String areaCode;

	@Schema(description = "城市区号")
	private String cityCode;

	@Schema(description = "父级编码")
	private String parentCode;

	@Schema(description = "父级等级（1：省级；城市；3：区县）")
	private Integer level;

	@Schema(description = "邮政编码")
	private String zipCode;

	@Schema(description = "城市经度")
	private String longitude;

	@Schema(description = "城市纬度")
	private String latitude;

	@Schema(description = "是否存在下级")
	private Boolean haveChild;

	@Schema(description = "创建者")
	private Long creator;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新者")
	private Long updater;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Boolean getHaveChild() {
		return haveChild;
	}
	public void setHaveChild(Boolean haveChild) {
		this.haveChild = haveChild;
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
			.append("pid", getPid())
			.append("name", getName())
			.append("pinyin", getPinyin())
			.append("shortName", getShortName())
			.append("fullName", getFullName())
			.append("areaCode", getAreaCode())
			.append("cityCode", getCityCode())
			.append("parentCode", getParentCode())
			.append("level", getLevel())
			.append("zipCode", getZipCode())
			.append("longitude", getLongitude())
			.append("latitude", getLatitude())
			.append("haveChild", getHaveChild())
			.append("creator", getCreator())
			.append("createTime", getCreateTime())
			.append("updater", getUpdater())
			.append("updateTime", getUpdateTime())
			.toString();
	}
}