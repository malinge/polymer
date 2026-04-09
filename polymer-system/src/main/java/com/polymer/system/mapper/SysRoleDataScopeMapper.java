package com.polymer.system.mapper;

import com.polymer.system.entity.SysRoleDataScopeEntity;
import com.polymer.system.query.SysRoleDataScopeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 角色数据权限
 *
 * @author polymer
 */
@Mapper
public interface SysRoleDataScopeMapper {

    /**
     * 根据角色ID，获取部门ID列表
     * @param roleId 角色id
     */
    List<Long> selectDeptIdsByRoleId(Long roleId);

    /**
     * 根据角色id集合和部门id集合删除角色数据权限
     *
     * @param roleIdList 角色id集合
     * @param deptIdList  部门id集合
     * @return 结果
     */
    int deleteByRoleIdAndDeptId(@Param("roleIdList") Collection<Long> roleIdList, @Param("deptIdList") Collection<Long> deptIdList);

    /**
     * 查询角色数据权限列表
     *
     * @param query 角色数据权限查询
     * @return 角色数据权限集合
     */
    List<SysRoleDataScopeEntity> selectSysRoleDataScopeList(SysRoleDataScopeQuery query);

    /**
     * 查询角色数据权限
     *
     * @param id 角色数据权限主键
     * @return 角色数据权限
     */
    SysRoleDataScopeEntity selectSysRoleDataScopeById(Long id);

    /**
     * 新增角色数据权限
     *
     * @param sysRoleDataScope 角色数据权限
     * @return 结果
     */
    int insertSysRoleDataScope(SysRoleDataScopeEntity sysRoleDataScope);

    /**
     * 修改角色数据权限
     *
     * @param sysRoleDataScope 角色数据权限
     * @return 结果
     */
    int updateSysRoleDataScope(SysRoleDataScopeEntity sysRoleDataScope);

    /**
     * 删除角色数据权限
     *
     * @param id 角色数据权限主键
     * @return 结果
     */
    int deleteSysRoleDataScopeById(Long id);

}