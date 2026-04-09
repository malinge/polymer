package com.polymer.generator.service;

import com.polymer.generator.entity.TableFieldEntity;

import java.util.List;

/**
 * 表字段
 *
 * polymer@126.com
 *
 */
public interface TableFieldService {

    List<TableFieldEntity> getByTableId(Long tableId);

    void deleteBatchTableIds(Long[] tableIds);

    /**
     * 修改表字段数据
     *
     * @param tableId        表ID
     * @param tableFieldList 字段列表
     */
    void updateTableField(Long tableId, List<TableFieldEntity> tableFieldList);

    /**
     * 初始化字段数据
     */
    void initFieldList(List<TableFieldEntity> tableFieldList);

    int insertTableField(TableFieldEntity tableField);

    /**
     * 修改代码生成表字段
     *
     * @param tableField 代码生成表字段
     * @return 结果
     */
    int updateTableField(TableFieldEntity tableField);

    void removeBatchByIds(List<Long> ids);
}