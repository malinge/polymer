package com.polymer.system.api;

import com.polymer.api.storage.StorageApi;
import com.polymer.api.system.SysCheckImportApi;
import com.polymer.api.system.dto.ImportResultDTO;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.ImportValidationResult;
import com.polymer.framework.common.utils.ExcelUtil;
import com.polymer.framework.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 导入校验公共接口实现
 *
 * @author polymer
 */
@Service
public class SysCheckImportApiImpl implements SysCheckImportApi {

    private static final Logger log = LoggerFactory.getLogger(SysCheckImportApiImpl.class);

    @Resource
    private StorageApi storageApi;

    @Override
    public <T> ImportResultDTO<T> validateImportFile(byte[] fileBytes,
                                                     String fileName,
                                                     Class<T> clazz,
                                                     int headerRowIndex,
                                                     int dataStartRowIndex) {
        ImportResultDTO<T> resultVO = new ImportResultDTO<>();

        try {
            // 1. 调用 ExcelUtil 校验
            ImportValidationResult<T> result = ExcelUtil.validateImportFile(fileBytes, clazz, headerRowIndex, dataStartRowIndex);

            // 2. 表头校验失败，生成错误文件并上传
            if (!result.getPassed()) {
                String errorFileUrl = null;

                // 如果有错误文件字节，上传
                if (result.getErrorFileBytes() != null) {
                    errorFileUrl = uploadErrorFile(result.getErrorFileBytes(), fileName);
                }

                String message = buildResultMessage(result.getTotalRowCount(), 0, result.getTotalRowCount(), 0, 0);
                resultVO.setPassed(false);
                resultVO.setErrorFileUrl(errorFileUrl);
                resultVO.setMessage(message);
                resultVO.setTotalRowCount(result.getTotalRowCount());
                return resultVO;
            }

            // 3. 校验通过
            resultVO.setPassed(true);
            resultVO.setDataList(result.getDataList());
        } catch (Exception e) {
            log.error("校验导入文件失败", e);
            resultVO.setPassed(false);
            resultVO.setMessage("校验失败：" + e.getMessage());
        }
        return resultVO;
    }

    @Override
    public <T> ImportResultDTO<T> validateImportFile(byte[] fileBytes, String fileName, Class<T> clazz) {
        return validateImportFile(fileBytes, fileName, clazz, 1, 2);
    }

    @Override
    public <E> String exportErrorFile(Class<E> clazz, List<E> errorDataList, String fileNamePrefix, String sheetName, String title) {
        if (errorDataList == null || errorDataList.isEmpty()) {
            log.warn("错误数据列表为空，无需导出");
            return null;
        }

        if(StringUtils.isBlank(sheetName)){
            sheetName="sheet1";
        }
        if(StringUtils.isBlank(title)){
            title="数据导入";
        }

        try {
            ExcelUtil<E> errorUtil = new ExcelUtil<>(clazz);
            String fileName = fileNamePrefix + "_" + System.currentTimeMillis();
            byte[] errorBytes = errorUtil.exportExcel(errorDataList, sheetName, title);

            // 上传到存储服务
            String path = storageApi.getPath(fileName + ".xlsx");
            return storageApi.upload(errorBytes, path);
        } catch (Exception e) {
            log.error("导出错误文件失败", e);
            throw new ServiceException("导出错误文件失败：" + e.getMessage());
        }
    }

    /**
     * 构建结果消息
     */
    @Override
    public String buildResultMessage(int totalCount, int successNum, int errorNum,
                                      int overrideNum, int skipNum) {
        StringBuilder format = new StringBuilder();
        format.append(String.format("导入总数据%d条，导入成功%d条", totalCount, successNum));

        if (overrideNum > 0) {
            format.append(String.format("（其中覆盖%d条）", overrideNum));
        }
        format.append(String.format("，失败%d条", errorNum));

        if (skipNum > 0) {
            format.append(String.format("（其中跳过%d条", skipNum));
            if (skipNum == errorNum) {
                format.append("）");
            } else {
                format.append(String.format("，错误%d条）", (errorNum - skipNum)));
            }
        }
        return format.toString();
    }


    // ========== 私有方法 ==========

    /**
     * 上传错误文件
     */
    private String uploadErrorFile(byte[] errorBytes, String originalFileName) {
        try {
            String fileName = "error_" + originalFileName;
            String path = storageApi.getPath(fileName);
            return storageApi.upload(errorBytes, path);
        } catch (Exception e) {
            log.error("上传错误文件失败", e);
            throw new ServiceException("上传错误文件失败：" + e.getMessage());
        }
    }

}