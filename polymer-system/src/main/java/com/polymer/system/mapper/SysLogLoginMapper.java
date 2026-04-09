package com.polymer.system.mapper;

import com.polymer.system.entity.SysLogLoginEntity;
import com.polymer.system.query.SysLogLoginQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 登录日志
 *
 * @author polymer
 */
@Mapper
public interface SysLogLoginMapper {

    /**
     * 查询登录日志列表
     *
     * @param query 登录日志查询
     * @return 登录日志集合
     */
    List<SysLogLoginEntity> selectSysLogLoginList(SysLogLoginQuery query);

    /**
     * 新增登录日志
     *
     * @param entity 登录日志
     * @return 结果
     */
    int insertLogLogin(SysLogLoginEntity entity);
}