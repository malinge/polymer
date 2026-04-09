package com.polymer.system.mapper;

import com.polymer.system.entity.SysParamsEntity;
import com.polymer.system.query.SysParamsQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 参数管理
 *
 * @author polymer
 */
@Mapper
public interface SysParamsMapper {

    /**
     * 根据参数判断是否存在
     *
     * @param paramKey 参数key
     * @return 结果
     */
    boolean isExist(String paramKey);

    /**
     * 根据参数key获取参数信息
     *
     * @param paramKey 参数key
     * @return 结果
     */
    SysParamsEntity get(String paramKey);

    /**
     * 根据参数查询获取参数集合
     *
     * @param query 参数查询
     * @return 结果
     */
    List<SysParamsEntity> selectSysParamsList(SysParamsQuery query);

    /**
     * 查询参数管理
     *
     * @param id 参数管理主键
     * @return 参数管理
     */
    SysParamsEntity selectSysParamsById(Long id);

    /**
     * 新增参数管理
     *
     * @param sysParams 参数管理
     * @return 结果
     */
    int insertSysParams(SysParamsEntity sysParams);

    /**
     * 修改参数管理
     *
     * @param sysParams 参数管理
     * @return 结果
     */
    int updateSysParams(SysParamsEntity sysParams);

    /**
     * 删除参数管理
     *
     * @param id 参数管理主键
     * @return 结果
     */
    int deleteSysParamsById(Long id);

    /**
     * 根据id集合查询获取参数集合
     *
     * @param list id集合
     * @return 结果
     */
    List<SysParamsEntity> batchSelectParamsByIds(List<Long> list);
}