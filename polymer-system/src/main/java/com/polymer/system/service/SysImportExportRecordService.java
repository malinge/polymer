package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.vo.SysImportExportRecordVO;
import com.polymer.system.query.SysImportExportRecordQuery;

import java.util.List;

/**
 * 导入导出记录表Service接口
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2026-06-29
 */
public interface SysImportExportRecordService {

     /**
     * 查询导入导出记录表分页列表
     *
     * @param query 查询条件
     * @return 导入导出记录表分页集合
     */
    PageResult<SysImportExportRecordVO> page(SysImportExportRecordQuery query);

    /**
     * 查询导入导出记录表
     *
     * @param id 导入导出记录表主键
     * @return 导入导出记录表
     */
    SysImportExportRecordVO selectSysImportExportRecordById(Long id);

    /**
     * 新增导入导出记录表
     *
     * @param vo 导入导出记录表
     * @return 结果
     */
    SysImportExportRecordVO insertSysImportExportRecord(SysImportExportRecordVO vo);

    /**
     * 批量新增导入导出记录表
     *
     * @param list 导入导出记录表集合
     * @return 结果
     */
    int batchInsertSysImportExportRecord(List<SysImportExportRecordVO> list);

    /**
     * 修改导入导出记录表
     *
     * @param vo 导入导出记录表
     * @return 结果
     */
     SysImportExportRecordVO updateSysImportExportRecord(SysImportExportRecordVO vo);

    /**
     * 批量修改导入导出记录表
     *
     * @param list 导入导出记录表集合
     * @return 结果
     */
    int batchUpdateSysImportExportRecord(List<SysImportExportRecordVO> list);

    /**
     * 删除导入导出记录表
     *
     * @param id 导入导出记录表主键
     * @return 结果
     */
    int deleteSysImportExportRecordById(Long id);

    /**
     * 批量删除导入导出记录表
     *
     * @param idList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSysImportExportRecordByIdList(List<Long> idList);

    List<SysImportExportRecordVO> list();
}