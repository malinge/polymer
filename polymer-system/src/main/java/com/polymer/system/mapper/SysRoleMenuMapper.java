package com.polymer.system.mapper;

import com.polymer.system.entity.SysRoleMenuEntity;
import com.polymer.system.query.SysRoleMenuQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author polymer
 */
@Mapper
public interface SysRoleMenuMapper {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> getMenuIdList(Long roleId);

    /**
     * 根据角色id集合和菜单id集合删除角色菜单关系信息
     *
     * @param roleIdList 角色id集合
     * @param menuIdList 菜单id集合
     * @return 结果
     */
    int deleteByRoleIdAndMenuId(@Param("roleIdList") Collection<Long> roleIdList, @Param("menuIdList") Collection<Long> menuIdList);

    /**
     * 查询角色菜单关系列表
     *
     * @param query 角色菜单关系查询
     * @return 角色菜单关系集合
     */
    List<SysRoleMenuEntity> selectSysRoleMenuList(SysRoleMenuQuery query);

    /**
     * 查询角色菜单关系
     *
     * @param id 角色菜单关系主键
     * @return 角色菜单关系
     */
    SysRoleMenuEntity selectSysRoleMenuById(Long id);

    /**
     * 新增角色菜单关系
     *
     * @param sysRoleMenu 角色菜单关系
     * @return 结果
     */
    int insertSysRoleMenu(SysRoleMenuEntity sysRoleMenu);

    /**
     * 修改角色菜单关系
     *
     * @param sysRoleMenu 角色菜单关系
     * @return 结果
     */
    int updateSysRoleMenu(SysRoleMenuEntity sysRoleMenu);

    /**
     * 删除角色菜单关系
     *
     * @param id 角色菜单关系主键
     * @return 结果
     */
    int deleteSysRoleMenuById(Long id);

    /**
     * 根据菜单id删除菜单关系信息
     *
     * @param menuId 菜单id
     * @return 结果
     */
    int deleteSysRoleMenuByMenuId(Long menuId);
}
