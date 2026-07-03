package com.polymer.system.api;

import com.polymer.api.storage.StorageApi;
import com.polymer.api.system.SysCheckImportApi;
import com.polymer.api.system.dto.ImportResultDTO;
import com.polymer.api.system.vo.ImportResultVO;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.ImportValidationResult;
import com.polymer.framework.common.utils.ExcelUtil;
import com.polymer.system.service.SysImportExportRecordService;
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
    @Resource
    private SysImportExportRecordService sysImportExportRecordService;

    @Override
    public <T> ImportResultDTO<T> validateImportFile(byte[] fileBytes, String fileName, Class<T> clazz, String strategy,
                                                     String businessType, int headerRowIndex, int dataStartRowIndex) {
        ImportResultDTO<T> resultDto = new ImportResultDTO<>();

        try {

            // 1.保存原始文件
            String path = storageApi.getPath(fileName);
            String resultFileUrl = storageApi.upload(fileBytes, path);
            resultDto.setResultFileUrl(resultFileUrl);

            // 2. 调用 ExcelUtil 校验
            ImportValidationResult<T> result = ExcelUtil.validateImportFile(fileBytes, clazz, headerRowIndex, dataStartRowIndex);

            // 3. 表头校验失败，生成错误文件并上传
            if (!result.getPassed()) {
                String errorFileUrl = null;

                // 如果有错误文件字节，上传
                if (result.getErrorFileBytes() != null) {
                    errorFileUrl = uploadErrorFile(result.getErrorFileBytes(), fileName);
                }

                String message = buildResultMessage(result.getTotalRowCount(), 0, result.getTotalRowCount(), 0, 0);
                resultDto.setPassed(false);
                resultDto.setErrorFileUrl(errorFileUrl);
                resultDto.setMessage(message);

                // 保存导入记录
                sysImportExportRecordService.insertSysImportExportRecord(businessType, "import",
                        result.getTotalRowCount(), 0, result.getTotalRowCount(), 0,
                        strategy, errorFileUrl, message, resultFileUrl);
                return resultDto;
            }

            // 4. 校验通过
            resultDto.setPassed(true);
            resultDto.setDataList(result.getDataList());
            if(resultDto.getDataList() == null || resultDto.getDataList().isEmpty()){
                String message = buildResultMessage(0, 0, 0, 0, 0);
                resultDto.setMessage(message);
                // 保存导入记录
                sysImportExportRecordService.insertSysImportExportRecord(businessType, "import",
                        0, 0, 0, 0, strategy, "",
                        message, resultFileUrl);
            }
        } catch (Exception e) {
            log.error("校验导入文件失败", e);
            resultDto.setPassed(false);
            resultDto.setMessage("校验失败：" + e.getMessage());
        }
        return resultDto;
    }

    @Override
    public <T> ImportResultDTO<T> validateImportFile(byte[] fileBytes, String fileName, Class<T> clazz, String strategy,
                                                     String businessType) {
        return validateImportFile(fileBytes, fileName, clazz, strategy, businessType, 1,
                2);
    }

    @Override
    public <E> ImportResultVO importResultProcessing(Class<E> clazz, List<E> errorDataList, int totalCount, int successNum,
                                                     int errorNum, int overrideNum, int skipNum, int conflictHandleCount,
                                                     String strategy, String resultFileUrl, String businessType) {
        ImportResultVO resultVO = new ImportResultVO();
        resultVO.setPassed(true);

        String message = buildResultMessage(totalCount, successNum, errorNum, overrideNum, skipNum);
        resultVO.setMessage(message);

        String sheetName="sheet1";
        String title= businessType + "数据导入";
        String errorFileUrl = "";
        if (errorDataList != null && !errorDataList.isEmpty()) {
            ExcelUtil<E> errorUtil = new ExcelUtil<>(clazz);
            String fileName = "data_" + System.currentTimeMillis();
            byte[] errorBytes = errorUtil.exportExcel(errorDataList, sheetName, title);

            // 上传到存储服务
            String path = storageApi.getPath(fileName + ".xlsx");
            errorFileUrl = storageApi.upload(errorBytes, path);
            resultVO.setPassed(false);
            resultVO.setErrorFileUrl(errorFileUrl);
        }

        sysImportExportRecordService.insertSysImportExportRecord(businessType, "import", totalCount,
                successNum, errorNum, conflictHandleCount, strategy, errorFileUrl, message, resultFileUrl);

        return resultVO;
    }

    @Override
    public void saveExportResult(byte[] fileBytes, int totalCount, String businessType) {
        String fileName = "data_" + System.currentTimeMillis();
        String path = storageApi.getPath(fileName + ".xlsx");
        String resultFileUrl = storageApi.upload(fileBytes, path);
        sysImportExportRecordService.insertSysImportExportRecord(businessType, "export", totalCount, resultFileUrl);
    }

    /**
     * 构建结果消息
     */
    private String buildResultMessage(int totalCount, int successNum, int errorNum,
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