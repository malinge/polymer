package com.polymer.generator.mapper;

import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.DatasourceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 数据源管理Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2025-10-21
*/
@Mapper
public interface DatasourceMapper {
    
    /**
     * 查询数据源管理列表
     * 
     * @param query 数据源管理查询
     * @return 数据源管理集合
     */
    List<DatasourceEntity> selectDatasourceList(Query query);

    /**
     * 查询数据源管理
     *
     * @param id 数据源管理主键
     * @return 数据源管理
     */
    DatasourceEntity selectDatasourceById(Long id);

    /**
     * 新增数据源管理
     * 
     * @param datasource 数据源管理
     * @return 结果
     */
    int insertDatasource(DatasourceEntity datasource);

    /**
     * 修改数据源管理
     * 
     * @param datasource 数据源管理
     * @return 结果
     */
    int updateDatasource(DatasourceEntity datasource);

    /**
     * 删除数据源管理
     * 
     * @param id 数据源管理主键
     * @return 结果
     */
    int deleteDatasourceById(Long id);

}