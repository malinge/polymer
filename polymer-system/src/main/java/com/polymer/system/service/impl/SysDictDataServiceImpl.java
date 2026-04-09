package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysDictDataEntity;
import com.polymer.system.mapper.SysDictDataMapper;
import com.polymer.system.query.SysDictDataQuery;
import com.polymer.system.service.SysDictDataService;
import com.polymer.system.vo.SysDictDataVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典
 *
 * @author polymer
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据字典数据查询获取分页字典数据集合
     *
     * @param query 字典数据查询
     * @return PageResult<SysDictDataVO>
     */
    @Override
    public PageResult<SysDictDataVO> page(SysDictDataQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysDictDataEntity> entityList = sysDictDataMapper.selectSysDictDataList(query);
        PageInfo<SysDictDataEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysDictDataVO::new), pageInfo.getTotal());
    }

    /**
     * 保存字典数据
     *
     * @param vo 字典数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictDataVO save(SysDictDataVO vo) {
        // 1. VO 转 Entity
        SysDictDataEntity entity = ConvertUtils.convertTo(vo, SysDictDataEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        sysDictDataMapper.insertSysDictData(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysDictDataVO::new);
    }

    /**
     * 更新字典数据
     *
     * @param vo 字典数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDictDataVO update(SysDictDataVO vo) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        SysDictDataEntity entity = ConvertUtils.convertTo(vo, SysDictDataEntity::new);
        // 3. 执行更新
        int rows = sysDictDataMapper.updateSysDictData(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysDictDataEntity updatedEntity = sysDictDataMapper.selectSysDictDataById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysDictDataVO::new);
    }

    /**
     * 根据字典数据主键集合删除字典数据
     *
     * @param idList 字典数据主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        batchUtils.executeBatch(SysDictDataMapper.class, idList, SysDictDataMapper::deleteSysDictDataById);
    }

    /**
     * 根据字典数据主键查询字典数据
     *
     * @param id 字典数据主键
     */
    @Override
    public SysDictDataVO getById(Long id) {
        SysDictDataEntity sysDictData = sysDictDataMapper.selectSysDictDataById(id);
        return ConvertUtils.convertTo(sysDictData, SysDictDataVO::new);
    }

}