package com.polymer.system.mapper;

import com.polymer.system.entity.SysLogErrorEntity;
import com.polymer.system.query.SysLogErrorQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志
 *
 * @author polymer
 */
@Mapper
public interface SysLogErrorMapper {

    /**
     * 物理删除指定时间之前的日志
     *
     * @param createTime 最大时间
     * @param limit      删除条数，防止一次删除太多
     * @return 删除条数
     */
    @Delete("DELETE FROM sys_log_error WHERE create_time < #{createTime} LIMIT #{limit}")
    Integer deleteByCreateTimeLt(@Param("createTime") LocalDateTime createTime, @Param("limit") Integer limit);

    /**
     * 查询错误日志列表
     *
     * @param query 错误日志查询
     * @return 错误日志集合
     */
    List<SysLogErrorEntity> selectSysLogErrorList(SysLogErrorQuery query);

    /**
     * 查询错误日志
     *
     * @param id 错误日志主键
     * @return 错误日志
     */
    SysLogErrorEntity selectSysLogErrorById(Long id);

    /**
     * 新增错误日志
     *
     * @param sysLogError 错误日志
     * @return 结果
     */
    int insertSysLogError(SysLogErrorEntity sysLogError);

    /**
     * 修改错误日志
     *
     * @param sysLogError 错误日志
     * @return 结果
     */
    int updateSysLogError(SysLogErrorEntity sysLogError);

    /**
     * 删除错误日志
     *
     * @param id 错误日志主键
     * @return 结果
     */
    int deleteSysLogErrorById(Long id);
}