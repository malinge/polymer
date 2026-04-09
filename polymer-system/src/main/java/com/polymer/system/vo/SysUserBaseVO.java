package com.polymer.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 用户基本信息
 *
 * @author polymer
 */
@Schema(description = "用户基本信息")
public class SysUserBaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "姓名不能为空")
    private String realName;

    @Schema(description = "性别 0：男   1：女   2：未知", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 2, message = "性别不正确")
    private Integer gender;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @Schema(description = "省份代码")
    private String provinceCode;

    @Schema(description = "程序代码")
    private String cityCode;

    @Schema(description = "地区代码")
    private String districtCode;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "所属城市")
    private List<String> city;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getCity() {
        return city;
    }

    public void setCity(List<String> city) {
        this.city = city;
    }
}
