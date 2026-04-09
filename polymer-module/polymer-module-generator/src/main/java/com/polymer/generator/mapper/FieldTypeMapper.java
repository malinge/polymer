package com.polymer.generator.mapper;

import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.FieldTypeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
* 字段类型管理Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2025-10-21
*/
@Mapper
public interface FieldTypeMapper {
    
    /**
     * 查询字段类型管理列表
     * 
     * @param query 字段类型管理查询
     * @return 字段类型管理集合
     */
    List<FieldTypeEntity> selectFieldTypeList(Query query);

    /**
     * 查询字段类型管理
     *
     * @param id 字段类型管理主键
     * @return 字段类型管理
     */
    FieldTypeEntity selectFieldTypeById(Long id);

    /**
     * 新增字段类型管理
     * 
     * @param fieldType 字段类型管理
     * @return 结果
     */
    int insertFieldType(FieldTypeEntity fieldType);

    /**
     * 修改字段类型管理
     * 
     * @param fieldType 字段类型管理
     * @return 结果
     */
    int updateFieldType(FieldTypeEntity fieldType);

    /**
     * 删除字段类型管理
     * 
     * @param id 字段类型管理主键
     * @return 结果
     */
    int deleteFieldTypeById(Long id);

    /**
     * 根据tableId，获取包列表
     */
    Set<String> getPackageByTableId(Long tableId);

    /**
     * 获取全部字段类型
     */
    Set<String> list();

}