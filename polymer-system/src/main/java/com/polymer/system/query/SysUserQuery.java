package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

/**
 * 用户查询
 *
 * @author polymer
 */
@Schema(description = "用户查询")
public class SysUserQuery extends PageParam {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "部门ID")
    private Long deptId;

    private Set<Long> deptIds;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Set<Long> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(Set<Long> deptIds) {
        this.deptIds = deptIds;
    }
}
