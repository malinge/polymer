package com.polymer.generator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.BaseClassEntity;
import com.polymer.generator.mapper.BaseClassMapper;
import com.polymer.generator.service.BaseClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 基类管理
 * polymer@126.com
 *
 */
@Service
public class BaseClassServiceImpl implements BaseClassService {

    @Resource
    private BaseClassMapper baseClassMapper;

    @Override
    public PageResult<BaseClassEntity> page(Query query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<BaseClassEntity> entityList = baseClassMapper.selectBaseClassList(query);
        PageInfo<BaseClassEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(entityList, pageInfo.getTotal());
    }

    @Override
    public List<BaseClassEntity> getList() {
        return baseClassMapper.selectBaseClassList(null);
    }

    @Override
    public BaseClassEntity save(BaseClassEntity entity) {
        entity.setCreateTime(LocalDateTime.now());
        baseClassMapper.insertBaseClass(entity);
        return entity;
    }

    @Override
    public BaseClassEntity updateBaseClass(BaseClassEntity baseClass) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (baseClass.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 执行更新
        int rows = baseClassMapper.updateBaseClass(baseClass);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        BaseClassEntity updatedEntity = baseClassMapper.selectBaseClassById(baseClass.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return updatedEntity;
    }

    @Override
    public void removeBatchByIds(List<Long> ids) {
        for(Long id: ids){
            baseClassMapper.deleteBaseClassById(id);
        }
    }

    @Override
    public BaseClassEntity selectBaseClassById(Long id) {
        return baseClassMapper.selectBaseClassById(id);
    }
}