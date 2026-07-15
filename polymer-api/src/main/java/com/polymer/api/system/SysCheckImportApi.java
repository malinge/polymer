package com.polymer.api.system;

import com.polymer.api.system.dto.ImportResultDTO;
import com.polymer.api.system.vo.ImportResultVO;

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
                                              Class<T> clazz, String strategy,
                                              String businessType,
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
    <T> ImportResultDTO<T> validateImportFile(byte[] fileBytes, String fileName, Class<T> clazz, String strategy, String businessType);

    /**
     * 导入结果处理
     */
    <E> ImportResultVO importResultProcessing(Class<E> clazz, List<E> errorDataList, int totalCount, int successNum, int errorNum, int overrideNum, int skipNum, int conflictHandleCount,
                                              String strategy, String resultFileUrl, String businessType);

     void saveExportResult(byte[] fileBytes, int totalCount, String businessType);

    <E> List<String> getUniqueFields(Class<E> clazz);

}