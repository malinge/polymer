package com.polymer.system.mapper;

import com.polymer.framework.mybatis.core.annotation.DataScope;
import com.polymer.system.entity.SysRoleEntity;
import com.polymer.system.query.SysRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理
 *
 * @author polymer
 */
@Mapper
public interface SysRoleMapper {

    /**
     * 获取用户所有角色
     *
     * @param userId 用户id
     * @return 角色管理集合
     */
    List<SysRoleEntity> selectSysRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID，获取用户角色编码
     */
    List<String> geRoleCodeByUserId(Long userId);

    /**
     * 查询角色管理列表
     *
     * @param query 角色管理查询
     * @return 角色管理集合
     */
    @DataScope
    List<SysRoleEntity> selectSysRoleList(SysRoleQuery query);

    /**
     * 查询角色管理
     *
     * @param id 角色管理主键
     * @return 角色管理
     */
    SysRoleEntity selectSysRoleById(Long id);

    /**
     * 新增角色管理
     *
     * @param sysRole 角色管理
     * @return 结果
     */
    int insertSysRole(SysRoleEntity sysRole);

    /**
     * 修改角色管理
     *
     * @param sysRole 角色管理
     * @return 结果
     */
    int updateSysRole(SysRoleEntity sysRole);

    /**
     * 删除角色管理
     *
     * @param id 角色管理主键
     * @return 结果
     */
    int deleteSysRoleById(Long id);

    /**
     * 根据主键id集合获取角色名称集合
     *
     * @param idList 数据主键集合
     * @return 结果
     */
    List<String> selectSysRoleNameByidList(List<Long> idList);
}
