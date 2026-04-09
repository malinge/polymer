package com.polymer.system.mapper;

import com.polymer.system.entity.SysLogOperateEntity;
import com.polymer.system.query.SysLogOperateQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 操作日志
 *
 * @author polymer
 */
@Mapper
public interface SysLogOperateMapper {

    /**
     * 查询操作日志列表
     *
     * @param query 操作日志查询
     * @return 操作日志集合
     */
    List<SysLogOperateEntity> selectSysLogOperateList(SysLogOperateQuery query);

    /**
     * 查询操作日志
     *
     * @param id 操作日志主键
     * @return 操作日志
     */
    SysLogOperateEntity selectSysLogOperateById(Long id);

    /**
     * 新增操作日志
     *
     * @param sysLogOperate 操作日志
     * @return 结果
     */
    int insertSysLogOperate(SysLogOperateEntity sysLogOperate);

    /**
     * 修改操作日志
     *
     * @param sysLogOperate 操作日志
     * @return 结果
     */
    int updateSysLogOperate(SysLogOperateEntity sysLogOperate);

    /**
     * 删除操作日志
     *
     * @param id 操作日志主键
     * @return 结果
     */
    int deleteSysLogOperateById(Long id);
}