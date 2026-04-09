package com.polymer.system.service.impl;

import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysRoleMenuEntity;
import com.polymer.system.mapper.SysRoleMenuMapper;
import com.polymer.system.service.SysRoleMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色与菜单对应关系
 *
 * @author polymer
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 保存或修改
     *
     * @param roleId     角色ID
     * @param menuIdList 菜单ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        // 数据库菜单ID列表
        List<Long> dbMenuIdList = getMenuIdList(roleId);

        // 需要新增的菜单ID
        Collection<Long> insertMenuIdList = CollectionUtils.subtract(menuIdList, dbMenuIdList);
        if (CollectionUtils.isNotEmpty(insertMenuIdList)) {
            List<SysRoleMenuEntity> menuList = insertMenuIdList.stream().map(menuId -> {
                SysRoleMenuEntity entity = new SysRoleMenuEntity();
                entity.setMenuId(menuId);
                entity.setRoleId(roleId);
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            batchUtils.executeBatch(SysRoleMenuMapper.class, menuList, SysRoleMenuMapper::insertSysRoleMenu);
        }

        // 需要删除的菜单ID
        Collection<Long> deleteMenuIdList = CollectionUtils.subtract(dbMenuIdList, menuIdList);
        if (CollectionUtils.isNotEmpty(deleteMenuIdList)) {
            List<Long> roleIdList = new ArrayList<>();
            roleIdList.add(roleId);
            sysRoleMenuMapper.deleteByRoleIdAndMenuId(roleIdList, deleteMenuIdList);
        }
    }

    /**
     * 根据角色ID，获取菜单ID列表
     *
     * @param roleId 角色ID
     * @return List<Long>
     */
    @Override
    public List<Long> getMenuIdList(Long roleId) {
        return sysRoleMenuMapper.getMenuIdList(roleId);
    }

    /**
     * 根据角色id列表，删除角色菜单关系
     *
     * @param roleIdList 角色id列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleIdList(List<Long> roleIdList) {
        sysRoleMenuMapper.deleteByRoleIdAndMenuId(roleIdList, null);
    }

    /**
     * 根据菜单id，删除角色菜单关系
     *
     * @param menuId 菜单id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByMenuId(Long menuId) {

        sysRoleMenuMapper.deleteSysRoleMenuByMenuId(menuId);
    }

}