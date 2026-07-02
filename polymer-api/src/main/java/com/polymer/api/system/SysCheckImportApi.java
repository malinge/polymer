package com.polymer.api.system;

import com.polymer.api.system.dto.ImportResultDTO;

import java.util.List;

/**
 * 导入校验公共接口
 * 提供导入相关的公共方法
 *
 * @author polymer
 */
public interface SysCheckImportApi {

    /**
     * 校验导入文件（包含表头检查）
     * 如果表头有错误，自动生成错误文件并上传
     *
     * @param fileBytes 文件字节数组
     * @param fileName 文件名
     * @param clazz 数据实体类
     * @param headerRowIndex 表头行索引（从0开始）
     * @param dataStartRowIndex 数据开始行索引（从0开始）
     * @param <T> 数据类型
     * @return 校验结果
     */
    <T> ImportResultDTO<T> validateImportFile(byte[] fileBytes,
                                              String fileName,
                                              Class<T> clazz,
                                              int headerRowIndex,
                                              int dataStartRowIndex);

    /**
     * 校验导入文件（使用默认行索引：表头在第2行索引1，数据从第3行索引2开始）
     *
     * @param fileBytes 文件字节数组
     * @param fileName 文件名
     * @param clazz 数据实体类
     * @param <T> 数据类型
     * @return 校验结果
     */
    <T> ImportResultDTO<T> validateImportFile(byte[] fileBytes, String fileName, Class<T> clazz);

    /**
     * 导出错误数据并上传
     * @param errorDataList 错误数据列表
     * @param clazz 错误数据实体类
     * @param fileNamePrefix 文件名前缀
     * @param sheetName Sheet名称
     * @param title 标题
     * @param <E> 错误数据类型
     * @return 错误文件URL
     */
    <E> String exportErrorFile(Class<E> clazz, List<E> errorDataList, String fileNamePrefix, String sheetName, String title);


    String buildResultMessage(int totalCount, int successNum, int errorNum,
                              int overrideNum, int skipNum);
}