package com.polymer.generator.mapper;

import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.TableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 代码生成表Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2025-10-21
*/
@Mapper
public interface TableMapper {
    
    /**
     * 查询代码生成表列表
     * 
     * @param query 代码生成表查询
     * @return 代码生成表集合
     */
    List<TableEntity> selectTableList(Query query);
    List<TableEntity> selectTableListByTableName(@Param("tableName") String tableName);

    /**
     * 查询代码生成表
     *
     * @param id 代码生成表主键
     * @return 代码生成表
     */
    TableEntity selectTableById(Long id);

    /**
     * 新增代码生成表
     * 
     * @param table 代码生成表
     * @return 结果
     */
    int insertTable(TableEntity table);

    /**
     * 修改代码生成表
     * 
     * @param table 代码生成表
     * @return 结果
     */
    int updateTable(TableEntity table);

    /**
     * 删除代码生成表
     * 
     * @param id 代码生成表主键
     * @return 结果
     */
    int deleteTableById(Long id);

}