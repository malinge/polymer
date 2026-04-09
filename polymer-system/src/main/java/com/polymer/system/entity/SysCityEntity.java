package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 城市管理对象 sys_city
 *
 * @author polymer zhangxf@126.com
 * @since 1.0.0 2025-07-18
 */
public class SysCityEntity extends BaseEntity  {
    private static final long serialVersionUID = 1L;
    /**
    * 父级id
    */
	private Long pid;
    /**
    * 城市名称
    */
	private String name;
    /**
    * 城市拼音
    */
	private String pinyin;
    /**
    * 城市简称
    */
	private String shortName;
    /**
    * 城市全称
    */
	private String fullName;
    /**
    * 行政编码
    */
	private String areaCode;
    /**
    * 城市区号
    */
	private String cityCode;
    /**
    * 父级编码
    */
	private String parentCode;
    /**
    * 父级等级（1：省级；城市；3：区县）
    */
	private Integer level;
    /**
    * 邮政编码
    */
	private String zipCode;
    /**
    * 城市经度
    */
	private String longitude;
    /**
    * 城市纬度
    */
	private String latitude;
    /**
    * 是否存在下级
    */
	private Boolean haveChild;

    public Long getPid() {
    return pid;
    }
    public void setPid(Long pid) {
        this.pid = pid;
    }
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
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
            .toString();
    }
}