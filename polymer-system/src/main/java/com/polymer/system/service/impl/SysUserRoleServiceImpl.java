package com.polymer.system.service.impl;

import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysUserRoleEntity;
import com.polymer.system.mapper.SysUserRoleMapper;
import com.polymer.system.service.SysUserRoleService;
import com.polymer.system.service.SysUserTokenService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关系
 *
 * @author polymer
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysUserTokenService sysUserTokenService;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 保存或修改
     *
     * @param userId     用户ID
     * @param roleIdList 角色ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        // 数据库角色ID列表
        List<Long> dbRoleIdList = getRoleIdList(userId);

        // 需要新增的角色ID
        Collection<Long> insertRoleIdList = CollectionUtils.subtract(roleIdList, dbRoleIdList);
        if (CollectionUtils.isNotEmpty(insertRoleIdList)) {
            List<SysUserRoleEntity> roleList = insertRoleIdList.stream().map(roleId -> {
                SysUserRoleEntity entity = new SysUserRoleEntity();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            batchUtils.executeBatch(SysUserRoleMapper.class, roleList, SysUserRoleMapper::insertSysUserRole);

        }

        // 需要删除的角色ID
        Collection<Long> deleteRoleIdList = CollectionUtils.subtract(dbRoleIdList, roleIdList);
        if (CollectionUtils.isNotEmpty(deleteRoleIdList)) {
            List<Long> userIdList = new ArrayList<>();
            userIdList.add(userId);
            sysUserRoleMapper.deleteByUserIdAndRoleId(userIdList, deleteRoleIdList);
        }
    }

    /**
     * 分配角色给用户列表
     *
     * @param roleId     角色ID
     * @param userIdList 用户ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserList(Long roleId, List<Long> userIdList) {
        List<SysUserRoleEntity> list = new ArrayList<>();
        for (Long userId : userIdList) {
            SysUserRoleEntity entity = new SysUserRoleEntity();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            list.add(entity);
        }
        // 批量新增
        batchUtils.executeBatch(SysUserRoleMapper.class, list, SysUserRoleMapper::insertSysUserRole);

        // 更新用户的缓存权限
        userIdList.forEach(sysUserTokenService::updateCacheAuthByUserId);
    }

    /**
     * 根据角色id列表，删除用户角色关系
     *
     * @param roleIdList 角色id
     */
    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        sysUserRoleMapper.deleteByUserIdAndRoleId(null, roleIdList);
    }

    /**
     * 根据用户id列表，删除用户角色关系
     *
     * @param userIdList 用户id列表
     */
    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        sysUserRoleMapper.deleteByUserIdAndRoleId(userIdList, null);
    }

    /**
     * 根据角色id、用户id列表，删除用户角色关系
     *
     * @param roleId     角色id
     * @param userIdList 用户id列表
     */
    @Override
    public void deleteByUserIdList(Long roleId, List<Long> userIdList) {
        List<Long> roleIdList = new ArrayList<>();
        roleIdList.add(roleId);
        sysUserRoleMapper.deleteByUserIdAndRoleId(roleIdList, userIdList);


        // 更新用户的缓存权限
        userIdList.forEach(sysUserTokenService::updateCacheAuthByUserId);
    }

    /**
     * 角色ID列表
     *
     * @param userId 用户ID
     * @return List<Long>
     */
    @Override
    public List<Long> getRoleIdList(Long userId) {
        return sysUserRoleMapper.getRoleIdList(userId);
    }

    /**
     * 用户角色列表
     *
     * @param roleId 角色ID
     * @return List<SysUserRoleEntity>
     */
    @Override
    public List<SysUserRoleEntity> getUserIdList(Long roleId) {
        return sysUserRoleMapper.getUserIdList(roleId);
    }
}