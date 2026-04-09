package com.polymer.system.mapper;


import com.polymer.system.entity.SysPostEntity;
import com.polymer.system.query.SysPostQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 岗位管理
 *
 * @author polymer
 */
@Mapper
public interface SysPostMapper {

    /**
     * 查询岗位管理列表
     *
     * @param query 岗位管理查询
     * @return 岗位管理集合
     */
    List<SysPostEntity> selectSysPostList(SysPostQuery query);

    /**
     * 查询岗位管理
     *
     * @param id 岗位管理主键
     * @return 岗位管理
     */
    SysPostEntity selectSysPostById(Long id);

    /**
     * 新增岗位管理
     *
     * @param sysPost 岗位管理
     * @return 结果
     */
    int insertSysPost(SysPostEntity sysPost);

    /**
     * 修改岗位管理
     *
     * @param sysPost 岗位管理
     * @return 结果
     */
    int updateSysPost(SysPostEntity sysPost);

    /**
     * 删除岗位管理
     *
     * @param id 岗位管理主键
     * @return 结果
     */
    int deleteSysPostById(Long id);
}