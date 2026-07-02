package com.polymer.system.mapper;

import java.util.List;
import com.polymer.system.entity.SysImportExportRecordEntity;
import com.polymer.system.query.SysImportExportRecordQuery;
import org.apache.ibatis.annotations.Mapper;

/**
* 导入导出记录表Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-29
*/
@Mapper
public interface SysImportExportRecordMapper {
    
    /**
     * 查询导入导出记录表列表
     * 
     * @param query 导入导出记录表查询
     * @return 导入导出记录表集合
     */
    List<SysImportExportRecordEntity> selectSysImportExportRecordList(SysImportExportRecordQuery query);

    /**
     * 查询导入导出记录表
     *
     * @param id 导入导出记录表主键
     * @return 导入导出记录表
     */
    SysImportExportRecordEntity selectSysImportExportRecordById(Long id);

    /**
     * 新增导入导出记录表
     * 
     * @param sysImportExportRecord 导入导出记录表
     * @return 结果
     */
    int insertSysImportExportRecord(SysImportExportRecordEntity sysImportExportRecord);

    /**
     * 修改导入导出记录表
     * 
     * @param sysImportExportRecord 导入导出记录表
     * @return 结果
     */
    int updateSysImportExportRecord(SysImportExportRecordEntity sysImportExportRecord);

    /**
     * 删除导入导出记录表
     * 
     * @param id 导入导出记录表主键
     * @return 结果
     */
    int deleteSysImportExportRecordById(Long id);

}