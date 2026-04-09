package com.polymer.system.mapper;

import com.polymer.system.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单管理
 *
 * @author polymer
 */
@Mapper
public interface SysMenuMapper {

    /**
     * 查询所有菜单列表
     *
     * @param type 菜单类型
     */
    List<SysMenuEntity> getMenuList(Integer type);

    /**
     * 查询用户菜单列表
     *
     * @param userId 用户ID
     * @param type   菜单类型
     */
    List<SysMenuEntity> getUserMenuList(Long userId, Integer type);

    /**
     * 查询用户权限列表
     *
     * @param userId 用户ID
     */
    List<String> getUserAuthorityList(Long userId);

    /**
     * 查询所有权限列表
     */
    List<String> getAuthorityList();

    /**
     * 获取所有菜单的id、pid列表
     */
    List<SysMenuEntity> getIdAndPidList();

    /**
     * 更新菜单信息
     */
    int updateSysMenu(SysMenuEntity sysMenu);

    /**
     * 获取下级菜单数量
     */
    int getSubMenuCount(Long pid);

    /**
     * 查询菜单管理
     *
     * @param id 菜单管理主键
     * @return 菜单管理
     */
    SysMenuEntity selectSysMenuById(Long id);

    /**
     * 新增菜单管理
     *
     * @param sysMenu 菜单管理
     * @return 结果
     */
    int insertSysMenu(SysMenuEntity sysMenu);


    /**
     * 删除菜单管理
     *
     * @param id 菜单管理主键
     * @return 结果
     */
    int deleteSysMenuById(Long id);
}
