package com.polymer.system.service.impl;

import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysRoleDataScopeEntity;
import com.polymer.system.mapper.SysRoleDataScopeMapper;
import com.polymer.system.service.SysRoleDataScopeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色数据权限
 *
 * @author polymer
 */
@Service
public class SysRoleDataScopeServiceImpl implements SysRoleDataScopeService {
    @Resource
    private SysRoleDataScopeMapper sysRoleDataScopeMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 保存或修改
     *
     * @param roleId    角色ID
     * @param deptIdList 部门ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        // 数据库部门ID列表
        List<Long> dbDeptIdList = getDeptIdList(roleId);

        // 需要新增的部门ID
        Collection<Long> insertDeptIdList = CollectionUtils.subtract(deptIdList, dbDeptIdList);
        if (CollectionUtils.isNotEmpty(insertDeptIdList)) {
            List<SysRoleDataScopeEntity> deptList = insertDeptIdList.stream().map(deptId -> {
                SysRoleDataScopeEntity entity = new SysRoleDataScopeEntity();
                entity.setDeptId(deptId);
                entity.setRoleId(roleId);
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            batchUtils.executeBatch(SysRoleDataScopeMapper.class, deptList, SysRoleDataScopeMapper::insertSysRoleDataScope);
        }

        // 需要删除的部门ID
        Collection<Long> deleteDeptIdList = CollectionUtils.subtract(dbDeptIdList, deptIdList);
        if (CollectionUtils.isNotEmpty(deleteDeptIdList)) {
            List<Long> roleIdList = new ArrayList<>();
            roleIdList.add(roleId);
            sysRoleDataScopeMapper.deleteByRoleIdAndDeptId(roleIdList, deleteDeptIdList);
        }
    }

    /**
     * 根据角色ID，获取部门ID列表
     *
     * @param roleId 角色主键
     * @return List<Long>
     */
    @Override
    public List<Long> getDeptIdList(Long roleId) {
        return sysRoleDataScopeMapper.selectDeptIdsByRoleId(roleId);
    }

    /**
     * 根据角色id列表，删除角色数据权限关系
     *
     * @param roleIdList 角色id列表
     */
    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        boolean isRoleIdValid = roleIdList != null && !roleIdList.isEmpty();
        if (!isRoleIdValid) {
            throw new IllegalArgumentException("角色ID不能为空！");
        }
        sysRoleDataScopeMapper.deleteByRoleIdAndDeptId(roleIdList, null);
    }
}