package com.polymer.generator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.FieldTypeEntity;
import com.polymer.generator.mapper.FieldTypeMapper;
import com.polymer.generator.service.FieldTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 字段类型管理
 * polymer@126.com
 */
@Service
public class FieldTypeServiceImpl implements FieldTypeService {
    @Resource
    private FieldTypeMapper fieldTypeMapper;

    @Override
    public PageResult<FieldTypeEntity> page(Query query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<FieldTypeEntity> entityList = fieldTypeMapper.selectFieldTypeList(query);
        PageInfo<FieldTypeEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(entityList, pageInfo.getTotal());
    }

    @Override
    public Map<String, FieldTypeEntity> getMap() {
        List<FieldTypeEntity> list = fieldTypeMapper.selectFieldTypeList(null);
        Map<String, FieldTypeEntity> map = new LinkedHashMap<>(list.size());
        for (FieldTypeEntity entity : list) {
            map.put(entity.getColumnType().toLowerCase(), entity);
        }
        return map;
    }

    @Override
    public Set<String> getPackageByTableId(Long tableId) {
        Set<String> importList = fieldTypeMapper.getPackageByTableId(tableId);
        return importList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getList() {
        return fieldTypeMapper.list();
    }

    @Override
    public FieldTypeEntity save(FieldTypeEntity entity) {
        entity.setCreateTime(LocalDateTime.now());
        fieldTypeMapper.insertFieldType(entity);

        return entity;
    }

    @Override
    public FieldTypeEntity getById(Long id) {
        return fieldTypeMapper.selectFieldTypeById(id);
    }

    @Override
    public FieldTypeEntity updateById(FieldTypeEntity entity) {
        fieldTypeMapper.updateFieldType(entity);

        // 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (entity.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 执行更新
        int rows = fieldTypeMapper.updateFieldType(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        FieldTypeEntity updatedEntity = fieldTypeMapper.selectFieldTypeById(entity.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return updatedEntity;
    }

    @Override
    public void removeBatchByIds(List<Long> list) {
        for(Long id: list){
            fieldTypeMapper.deleteFieldTypeById(id);
        }
    }
}