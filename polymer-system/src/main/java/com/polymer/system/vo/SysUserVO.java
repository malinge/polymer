package com.polymer.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户
 *
 * @author polymer
 */
@Schema(description = "用户")
public class SysUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "姓名不能为空")
    private String realName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别 0：男   1：女   2：未知", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 2, message = "性别不正确")
    private Integer gender;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    //@Sensitive(type = SensitiveTypeEnum.MOBILE_PHONE)
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

    @Schema(description = "部门ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门ID不能为空")
    private Long deptId;

    @Schema(description = "状态 0：停用    1：正常", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 1, message = "用户状态不正确")
    private Integer status;

    @Schema(description = "角色ID列表")
    private List<Long> roleIdList;

    @Schema(description = "角色名称列表")
    private List<String> roleNameList;

    @Schema(description = "岗位ID列表")
    private List<Long> postIdList;

    @Schema(description = "岗位名称列表")
    private List<String> postNameList;

    @Schema(description = "超级管理员   0：否   1：是")
    private Integer superAdmin;

    @Schema(description = "部门名称")
    private String orgName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<String> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public List<Long> getPostIdList() {
        return postIdList;
    }

    public void setPostIdList(List<Long> postIdList) {
        this.postIdList = postIdList;
    }

    public List<String> getPostNameList() {
        return postNameList;
    }

    public void setPostNameList(List<String> postNameList) {
        this.postNameList = postNameList;
    }

    public Integer getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Integer superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
