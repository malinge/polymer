package com.polymer.framework.common.pojo;

import java.util.List;

/**
 * 导入校验结果
 *
 * @author polymer
 */
public class ImportValidationResult<T> {

    /** 是否通过校验 */
    private Boolean passed;

    /** 数据列表 */
    private List<T> dataList;

    /** 错误文件字节 */
    private byte[] errorFileBytes;

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

    public byte[] getErrorFileBytes() {
        return errorFileBytes;
    }

    public void setErrorFileBytes(byte[] errorFileBytes) {
        this.errorFileBytes = errorFileBytes;
    }

    public int getTotalRowCount() {
        return totalRowCount;
    }

    public void setTotalRowCount(int totalRowCount) {
        this.totalRowCount = totalRowCount;
    }

}
