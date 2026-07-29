package com.polymer.system.service;

import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.vo.SysMenuVO;

import java.util.List;
import java.util.Set;


/**
 * 菜单管理
 *
 * @author polymer
 */
public interface SysMenuService {

    /**
     * 保存菜单
     *
     * @param vo 菜单信息
     */
    SysMenuVO save(SysMenuVO vo);

    /**
     * 更新菜单
     *
     * @param vo 菜单信息
     */
    SysMenuVO update(SysMenuVO vo);

    /**
     * 删除菜单
     *
     * @param id 菜单信息主键
     */
    void delete(Long id);

    /**
     * 菜单列表
     *
     * @param type 菜单类型
     */
    List<SysMenuVO> getMenuList(Integer type);

    /**
     * 用户菜单列表
     *
     * @param user 用户
     * @param type 菜单类型
     * @return List<SysMenuVO>
     */
    List<SysMenuVO> getUserMenuList(UserDetail user, Integer type);

    /**
     * 获取子菜单的数量
     *
     * @param pid 父菜单ID
     * @return Long
     */
    int getSubMenuCount(Long pid);

    /**
     * 获取用户权限列表
     *
     * @param user 登录用户信息
     * @return Set<String>
     */
    Set<String> getUserAuthority(UserDetail user);

    /**
     * 根据菜单ID，获取子菜单ID列表(包含本菜单ID)
     *
     * @param id 菜单ID
     */
    List<Long> getSubMenuIdList(Long id);

    /**
     * 根据菜单主键获取菜单信息
     *
     * @param id 菜单ID
     * @return SysMenuVO
     */
    SysMenuVO getById(Long id);
}
