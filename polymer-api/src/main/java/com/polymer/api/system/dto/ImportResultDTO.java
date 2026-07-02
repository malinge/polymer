package com.polymer.api.system.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 导入校验结果VO（API模块使用）
 *
 * @author polymer
 */
public class ImportResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 是否通过校验 */
    private Boolean passed;

    /** 数据列表 */
    private List<T> dataList;

    /** 错误文件相对路径 */
    private String errorFileUrl;

    /** 错误信息 */
    private String message;

    /** 总数据行数 */
    private int totalRowCount;

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
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

    public int getTotalRowCount() {
        return totalRowCount;
    }

    public void setTotalRowCount(int totalRowCount) {
        this.totalRowCount = totalRowCount;
    }
}
