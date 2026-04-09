package com.polymer.generator.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.config.GenDataSource;
import com.polymer.generator.entity.DatasourceEntity;

import java.util.List;

/**
 * 数据源管理
 * polymer@126.com
 *
 */
public interface DataSourceService {

    PageResult<DatasourceEntity> page(Query query);

    List<DatasourceEntity> getList();

    /**
     * 获取数据库产品名，如：MySQL
     *
     * @param datasourceId 数据源ID
     * @return 返回产品名
     */
    String getDatabaseProductName(Long datasourceId);

    /**
     * 根据数据源ID，获取数据源
     *
     * @param datasourceId 数据源ID
     */
    GenDataSource get(Long datasourceId);

    DatasourceEntity save(DatasourceEntity entity);

    /**
     * 查询数据源管理
     *
     * @param id 数据源管理主键
     * @return 数据源管理
     */
    DatasourceEntity selectDatasourceById(Long id);

    DatasourceEntity updateById(DatasourceEntity entity);

    void removeBatchByIds(List<Long> list);
}