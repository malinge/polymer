package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.system.entity.SysImportExportRecordEntity;
import com.polymer.system.mapper.SysImportExportRecordMapper;
import com.polymer.system.query.SysImportExportRecordQuery;
import com.polymer.system.service.SysImportExportRecordService;
import com.polymer.system.vo.SysImportExportRecordVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 导入导出记录表Service业务层处理
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2026-06-29
 */
@Service
public class SysImportExportRecordServiceImpl implements SysImportExportRecordService {
    @Resource
    private SysImportExportRecordMapper sysImportExportRecordMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 查询导入导出记录表分页列表
     *
     * @param query 查询条件
     * @return 导入导出记录表分页集合
     */
    @Override
    public PageResult<SysImportExportRecordVO> page(SysImportExportRecordQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysImportExportRecordEntity> entityList = sysImportExportRecordMapper.selectSysImportExportRecordList(query);
        PageInfo<SysImportExportRecordEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysImportExportRecordVO::new), pageInfo.getTotal());
    }

    /**
     * 查询导入导出记录表
     *
     * @param id 导入导出记录表主键
     * @return 导入导出记录表
     */
    @Override
    public SysImportExportRecordVO selectSysImportExportRecordById(Long id){
        SysImportExportRecordEntity entity =  sysImportExportRecordMapper.selectSysImportExportRecordById(id);
        return ConvertUtils.convertTo(entity, SysImportExportRecordVO::new);
    }

    /**
     * 新增导入导出记录表
     *
     * @param vo 导入导出记录表
     * @return 结果
     */
    @Override
    public SysImportExportRecordVO insertSysImportExportRecord(SysImportExportRecordVO vo){
        // 1. VO 转 Entity
        SysImportExportRecordEntity entity = ConvertUtils.convertTo(vo, SysImportExportRecordEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        sysImportExportRecordMapper.insertSysImportExportRecord(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysImportExportRecordVO::new);
    }

    /**
     * 批量新增导入导出记录表
     *
     * @param list 导入导出记录表集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsertSysImportExportRecord(List<SysImportExportRecordVO> list){
        List<SysImportExportRecordEntity> entityList = ConvertUtils.convertListTo(list, SysImportExportRecordEntity::new);
        return batchUtils.executeBatch(SysImportExportRecordMapper.class, entityList, SysImportExportRecordMapper::insertSysImportExportRecord);
    }

    /**
     * 修改导入导出记录表
     *
     * @param vo 导入导出记录表
     * @return 结果
     */
    @Override
    public SysImportExportRecordVO updateSysImportExportRecord(SysImportExportRecordVO vo){
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        SysImportExportRecordEntity entity = ConvertUtils.convertTo(vo, SysImportExportRecordEntity::new);
        // 3. 执行更新
        int rows = sysImportExportRecordMapper.updateSysImportExportRecord(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysImportExportRecordEntity updatedEntity = sysImportExportRecordMapper.selectSysImportExportRecordById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysImportExportRecordVO::new);
    }

    /**
     * 批量修改导入导出记录表
     *
     * @param list 导入导出记录表集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateSysImportExportRecord(List<SysImportExportRecordVO> list){
        List<SysImportExportRecordEntity> entityList = ConvertUtils.convertListTo(list, SysImportExportRecordEntity::new);
        return batchUtils.executeBatch(SysImportExportRecordMapper.class, entityList, SysImportExportRecordMapper::updateSysImportExportRecord);
    }

    /**
     * 删除导入导出记录表
     *
     * @param id 导入导出记录表主键
     * @return 结果
     */
    @Override
    public int deleteSysImportExportRecordById(Long id){
        return sysImportExportRecordMapper.deleteSysImportExportRecordById(id);
    }

    /**
     * 批量删除导入导出记录表
     *
     * @param idList 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteSysImportExportRecordByIdList(List<Long> idList){
        return batchUtils.executeBatch(SysImportExportRecordMapper.class, idList, SysImportExportRecordMapper::deleteSysImportExportRecordById);
    }

    @Override
    public List<SysImportExportRecordVO> list() {
        SysImportExportRecordQuery query = new SysImportExportRecordQuery();
        query.setCreator(SecurityUser.getUserId());
        query.setOperationType("import");
        List<SysImportExportRecordEntity> entityList = sysImportExportRecordMapper.selectSysImportExportRecordList(query);
        return ConvertUtils.convertListTo(entityList, SysImportExportRecordVO::new);
    }

    @Override
    public SysImportExportRecordVO insertSysImportExportRecord(String businessType, String operationTypeint, int totalCount,
                                                               int successNum, int errorNum,  int conflictHandleCount,
                                                               String strategy, String errorFileUrl, String message,
                                                               String resultFileUrl) {
        SysImportExportRecordVO record = new SysImportExportRecordVO();
        record.setBusinessType(businessType);
        record.setOperationType(operationTypeint);
        record.setOperatorName(SecurityUser.getRealName());
        record.setTotalCount(totalCount);
        record.setSuccessCount(successNum);
        record.setErrorCount(errorNum);
        record.setConflictHandleCount(conflictHandleCount);
        record.setImportStrategy(strategy);
        record.setErrorFileUrl(errorFileUrl);
        record.setRemark(message);
        record.setResultFileUrl(resultFileUrl);
        return insertSysImportExportRecord(record);
    }

    @Override
    public SysImportExportRecordVO insertSysImportExportRecord(String businessType, String operationTypeint, int totalCount,
                                                               String resultFileUrl) {
        return insertSysImportExportRecord(businessType, operationTypeint, totalCount, totalCount, 0,
                0 ,"", "", "", resultFileUrl);
    }
}