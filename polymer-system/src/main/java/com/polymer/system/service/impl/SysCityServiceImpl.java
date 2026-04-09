package com.polymer.system.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.TreeUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysCityEntity;
import com.polymer.system.mapper.SysCityMapper;
import com.polymer.system.query.SysCityQuery;
import com.polymer.system.service.SysCityService;
import com.polymer.system.vo.SysCityVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 城市管理Service业务层处理
 *
 * @author polymer zhangxf@126.com
 * @since 1.0.0 2025-07-18
 */
@Service
public class SysCityServiceImpl implements SysCityService {
    @Resource
    private SysCityMapper sysCityMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 查询城市管理分页列表
     *
     * @param query 查询条件
     * @return 城市管理分页集合
     */
    @Override
    public List<SysCityVO> selectSysCityList(SysCityQuery query) {
        List<SysCityEntity> entityList = sysCityMapper.selectSysCityList(query);
        return ConvertUtils.convertListTo(entityList, SysCityVO::new);
    }

    /**
     * 查询城市管理
     *
     * @param id 城市管理主键
     * @return 城市管理
     */
    @Override
    public SysCityVO selectSysCityById(Long id){
        SysCityEntity entity =  sysCityMapper.selectSysCityById(id);
        return ConvertUtils.convertTo(entity, SysCityVO::new);
    }

    /**
     * 新增城市管理
     *
     * @param vo 城市管理
     * @return 结果
     */
    @Override
    public SysCityVO insertSysCity(SysCityVO vo){
        if(vo.getPid() != 0){
            SysCityEntity entity = sysCityMapper.selectSysCityById(vo.getPid());
            if(entity != null){
                vo.setParentCode(entity.getAreaCode());
            }
        }else {
            vo.setParentCode("0");
        }

        // 1. VO 转 Entity
        SysCityEntity entity = ConvertUtils.convertTo(vo, SysCityEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        sysCityMapper.insertSysCity(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysCityVO::new);
    }

    /**
     * 批量新增城市管理
     *
     * @param list 城市管理集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsertSysCity(List<SysCityVO> list){
        List<SysCityEntity> entityList = ConvertUtils.convertListTo(list, SysCityEntity::new);
        return batchUtils.executeBatch(SysCityMapper.class, entityList, SysCityMapper::insertSysCity);
    }

    /**
     * 修改城市管理
     *
     * @param vo 城市管理
     * @return 结果
     */
    @Override
    public SysCityVO updateSysCity(SysCityVO vo){
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        SysCityEntity entity = ConvertUtils.convertTo(vo, SysCityEntity::new);
        // 3. 执行更新
        int rows = sysCityMapper.updateSysCity(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysCityEntity updatedEntity = sysCityMapper.selectSysCityById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysCityVO::new);
    }

    /**
     * 批量修改城市管理
     *
     * @param list 城市管理集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateSysCity(List<SysCityVO> list){
        List<SysCityEntity> entityList = ConvertUtils.convertListTo(list, SysCityEntity::new);
        return batchUtils.executeBatch(SysCityMapper.class, entityList, SysCityMapper::updateSysCity);
    }

    @Override
    public List<SysCityVO> selectSysCityTree() {
        List<SysCityEntity> sysCityEntities = sysCityMapper.selectSysCityList(null);
        List<SysCityVO> sysCityVOS = ConvertUtils.convertListTo(sysCityEntities, SysCityVO::new);
        return TreeUtils.build(sysCityVOS);
    }

}