package com.polymer.generator.mapper;

import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.TableFieldEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 代码生成表字段Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2025-10-21
*/
@Mapper
public interface TableFieldMapper {
    
    /**
     * 查询代码生成表字段列表
     * 
     * @param query 代码生成表字段查询
     * @return 代码生成表字段集合
     */
    List<TableFieldEntity> selectTableFieldList(Query query);

    /**
     * 查询代码生成表字段
     *
     * @param id 代码生成表字段主键
     * @return 代码生成表字段
     */
    TableFieldEntity selectTableFieldById(Long id);

    /**
     * 新增代码生成表字段
     * 
     * @param tableField 代码生成表字段
     * @return 结果
     */
    int insertTableField(TableFieldEntity tableField);

    /**
     * 修改代码生成表字段
     * 
     * @param tableField 代码生成表字段
     * @return 结果
     */
    int updateTableField(TableFieldEntity tableField);

    /**
     * 删除代码生成表字段
     * 
     * @param id 代码生成表字段主键
     * @return 结果
     */
    int deleteTableFieldById(Long id);

    void deleteBatchTableIds(Long[] tableIds);

}