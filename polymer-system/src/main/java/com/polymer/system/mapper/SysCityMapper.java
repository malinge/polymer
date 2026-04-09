package com.polymer.system.mapper;

import com.polymer.system.entity.SysCityEntity;
import com.polymer.system.query.SysCityQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 城市管理Mapper接口
*
* @author polymer zhangxf@126.com
* @since 1.0.0 2025-07-18
*/
@Mapper
public interface SysCityMapper {
    
    /**
     * 查询城市管理列表
     * 
     * @param query 城市管理查询
     * @return 城市管理集合
     */
    List<SysCityEntity> selectSysCityList(SysCityQuery query);

    /**
     * 查询城市管理
     *
     * @param id 城市管理主键
     * @return 城市管理
     */
    SysCityEntity selectSysCityById(Long id);

    /**
     * 新增城市管理
     * 
     * @param sysCity 城市管理
     * @return 结果
     */
    int insertSysCity(SysCityEntity sysCity);

    /**
     * 修改城市管理
     * 
     * @param sysCity 城市管理
     * @return 结果
     */
    int updateSysCity(SysCityEntity sysCity);


}