package com.polymer.generator.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.FieldTypeEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字段类型管理
 * polymer@126.com
 *
 */
public interface FieldTypeService {
    PageResult<FieldTypeEntity> page(Query query);

    Map<String, FieldTypeEntity> getMap();

    /**
     * 根据tableId，获取包列表
     *
     * @param tableId 表ID
     * @return 返回包列表
     */
    Set<String> getPackageByTableId(Long tableId);

    Set<String> getList();

    FieldTypeEntity save(FieldTypeEntity entity);

    FieldTypeEntity getById(Long id);

    FieldTypeEntity updateById(FieldTypeEntity entity);

    void removeBatchByIds(List<Long> list);
}