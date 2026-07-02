package com.polymer.api.system.vo;

import java.io.Serializable;

public class ImportResultVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 是否通过校验 */
    private Boolean passed;

    /** 错误文件相对路径 */
    private String errorFileUrl;

    /** 错误信息 */
    private String message;

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public String getErrorFileUrl() {
        return errorFileUrl;
    }

    public void setErrorFileUrl(String errorFileUrl) {
        this.errorFileUrl = errorFileUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
