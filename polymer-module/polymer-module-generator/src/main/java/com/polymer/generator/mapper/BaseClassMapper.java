package com.polymer.generator.mapper;

import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.BaseClassEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 基类管理Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2025-10-21
*/
@Mapper
public interface BaseClassMapper {
    
    /**
     * 查询基类管理列表
     * 
     * @param query 基类管理查询
     * @return 基类管理集合
     */
    List<BaseClassEntity> selectBaseClassList(Query query);

    /**
     * 查询基类管理
     *
     * @param id 基类管理主键
     * @return 基类管理
     */
    BaseClassEntity selectBaseClassById(Long id);

    /**
     * 新增基类管理
     * 
     * @param baseClass 基类管理
     * @return 结果
     */
    int insertBaseClass(BaseClassEntity baseClass);

    /**
     * 修改基类管理
     * 
     * @param baseClass 基类管理
     * @return 结果
     */
    int updateBaseClass(BaseClassEntity baseClass);

    /**
     * 删除基类管理
     * 
     * @param id 基类管理主键
     * @return 结果
     */
    int deleteBaseClassById(Long id);

}