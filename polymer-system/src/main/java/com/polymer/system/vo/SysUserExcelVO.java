package com.polymer.system.vo;

import com.polymer.framework.common.annotation.Excel;
import com.polymer.framework.common.annotation.Excel.Type;

import java.io.Serializable;

/**
 * excel用户表
 *
 * @author polymer
 */
public class SysUserExcelVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 本属性对于导出无用，只是用于翻译
     */
    private Long id;

    @Excel(name = "用户账号")
    private String username;

    @Excel(name = "用户姓名")
    private String realName;

    @Excel(name = "用户性别", dictType = "user_gender")
    private Integer gender;

    @Excel(name = "用户邮箱")
    private String email;

    @Excel(name = "手机号码")
    private String mobile;

    @Excel(name = "用户状态", dictType = "user_status")
    private Integer status;

    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

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


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
