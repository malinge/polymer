package com.polymer.system.mapper;

import com.polymer.system.entity.SysAppDetailsEntity;
import com.polymer.system.query.SysAppDetailsQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * app信息表Mapper接口
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2025-05-09
 */
@Mapper
public interface SysAppDetailsMapper {

    /**
     * 查询app信息表列表
     *
     * @param query app信息表查询
     * @return app信息表集合
     */
    List<SysAppDetailsEntity> selectSysAppDetailsList(SysAppDetailsQuery query);

    /**
     * 查询app信息表
     *
     * @param id app信息表主键
     * @return app信息表
     */
    SysAppDetailsEntity selectSysAppDetailsById(Integer id);

    /**
     * 新增app信息表
     *
     * @param sysAppDetails app信息表
     * @return 结果
     */
    int insertSysAppDetails(SysAppDetailsEntity sysAppDetails);

    /**
     * 修改app信息表
     *
     * @param sysAppDetails app信息表
     * @return 结果
     */
    int updateSysAppDetails(SysAppDetailsEntity sysAppDetails);

}