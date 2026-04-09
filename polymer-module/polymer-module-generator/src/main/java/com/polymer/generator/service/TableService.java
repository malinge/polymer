package com.polymer.generator.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.TableEntity;

/**
 * 数据表
 * polymer@126.com
 *
 */
public interface TableService {

    PageResult<TableEntity> page(Query query);

    TableEntity getByTableName(String tableName);

    void deleteBatchIds(Long[] ids);

    /**
     * 导入表
     *
     * @param datasourceId 数据源ID
     * @param tableName    表名
     */
    void tableImport(Long datasourceId, String tableName);

    /**
     * 同步数据库表
     *
     * @param id 表ID
     */
    void sync(Long id);

    TableEntity getById(Long id);

    TableEntity updateById(TableEntity table);
}