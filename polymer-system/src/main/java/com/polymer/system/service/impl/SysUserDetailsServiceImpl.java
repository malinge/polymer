package com.polymer.system.service.impl;

import com.polymer.framework.mybatis.core.enums.DataScopeEnum;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.entity.SysRoleEntity;
import com.polymer.system.enums.UserStatusEnum;
import com.polymer.system.mapper.SysRoleDataScopeMapper;
import com.polymer.system.mapper.SysRoleMapper;
import com.polymer.system.service.SysDeptService;
import com.polymer.system.service.SysMenuService;
import com.polymer.system.service.SysUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户 UserDetails 信息
 *
 * @author polymer
 */
@Service
public class SysUserDetailsServiceImpl implements SysUserDetailsService {
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleDataScopeMapper sysRoleDataScopeMapper;

    /**
     * 获取 UserDetails 对象，设置用户权限信息
     *
     * @param userDetail 登录用户信息
     * @return UserDetails
     */
    @Override
    public UserDetails getUserDetails(UserDetail userDetail) {
        // 账号不可用
        if (userDetail.getStatus() == UserStatusEnum.DISABLE.getValue()) {
            userDetail.setEnabled(false);
        }

        // 数据权限范围
        setDataScope(userDetail);

        // 用户权限列表
        Set<String> authoritySet = sysMenuService.getUserAuthority(userDetail);

        // 用户角色编码列表
        List<String> roleCodeList = sysRoleMapper.geRoleCodeByUserId(userDetail.getId());
        roleCodeList.forEach(roleCode -> authoritySet.add("ROLE_" + roleCode));

        userDetail.setAuthoritySet(authoritySet);

        return userDetail;
    }

    /**
     * 设置当前用户的数据权限
     */
    public void setDataScope(UserDetail userDetail) {
        // 1. 获取用户所有角色
        List<SysRoleEntity> roleEntities = sysRoleMapper.selectSysRolesByUserId(userDetail.getId());
        if (roleEntities == null || roleEntities.isEmpty()) {
            userDetail.setDataScope(DataScopeEnum.SELF.getValue());
            userDetail.setDataScopeList(Collections.emptyList());
            return;
        }
        //本架构采用的优先级排序：ALL > CUSTOM > DEPT_AND_CHILD > DEPT_ONLY > SELF
        Optional<SysRoleEntity> minRole = roleEntities.stream()
                .min(Comparator.comparingInt(SysRoleEntity::getDataScope));

        SysRoleEntity role = minRole.get();

        // 2. 解析角色数据权限
        resolveRoleDataScope(userDetail, role);

        // 3. 部门负责人权限增强
        enhanceByLeaderPermission(userDetail);
    }


    private void resolveRoleDataScope(UserDetail userDetail, SysRoleEntity role) {
        if (role.getDataScope().equals(DataScopeEnum.ALL.getValue())) {
            // 全部数据权限，则返回null
            userDetail.setDataScope(DataScopeEnum.ALL.getValue());
            userDetail.setDataScopeList(Collections.emptyList());
        } else if (role.getDataScope().equals(DataScopeEnum.CUSTOM.getValue())) {
            // 自定义数据权限范围
            List<Long> deptIds = sysRoleDataScopeMapper.selectDeptIdsByRoleId(role.getId());
            userDetail.setDataScope(DataScopeEnum.CUSTOM.getValue());
            userDetail.setDataScopeList(deptIds);
        } else if (role.getDataScope().equals(DataScopeEnum.DEPT_AND_CHILD.getValue())) {
            // 本部门及子部门数据
            List<Long> deptIds = sysDeptService.getDeptTreeIds(userDetail.getDeptId());
            userDetail.setDataScope(DataScopeEnum.DEPT_AND_CHILD.getValue());
            userDetail.setDataScopeList(deptIds);
        } else if (role.getDataScope().equals(DataScopeEnum.DEPT_ONLY.getValue())) {
            // 本部门数据
            userDetail.setDataScope(DataScopeEnum.DEPT_ONLY.getValue());
            userDetail.setDataScopeList(Collections.singletonList(userDetail.getDeptId()));
        }
    }

    // 部门负责人权限增强
    private void enhanceByLeaderPermission(UserDetail userDetail) {
        List<Long> leaderDeptIds = sysDeptService.getDeptIdsByLeaderId(userDetail.getId());
        if (leaderDeptIds.isEmpty()) {
            return ;
        }

        // 负责人权限覆盖规则：
        // 1. 原权限为SELF/DEPT -> 提升为自定义权限（负责的部门）
        // 2. 其他情况保持原权限，但增加负责部门的访问权
        Integer dataScope = userDetail.getDataScope();
        if (Objects.equals(dataScope, DataScopeEnum.SELF.getValue()) || Objects.equals(dataScope, DataScopeEnum.DEPT_ONLY.getValue())) {
            userDetail.setDataScope(DataScopeEnum.CUSTOM.getValue());
            userDetail.setDataScopeList(leaderDeptIds);
        }else if (Objects.equals(dataScope, DataScopeEnum.DEPT_AND_CHILD.getValue())) {
            List<Long> mergedOrgs = new ArrayList<>(userDetail.getDataScopeList());
            mergedOrgs.addAll(leaderDeptIds);

            List<Long> allDeptIds = new ArrayList<>();
            for (Long deptId : mergedOrgs) {
                allDeptIds.addAll(sysDeptService.getDeptTreeIds(deptId));
            }
            List<Long> deptIds = allDeptIds.stream().distinct().collect(Collectors.toList());
            userDetail.setDataScope(DataScopeEnum.DEPT_AND_CHILD.getValue());
            userDetail.setDataScopeList(deptIds);
        }else if (Objects.equals(dataScope, DataScopeEnum.CUSTOM.getValue())) {
            List<Long> mergedOrgs = new ArrayList<>(userDetail.getDataScopeList());
            mergedOrgs.addAll(leaderDeptIds);
            List<Long> deptIds = mergedOrgs.stream().distinct().collect(Collectors.toList());
            userDetail.setDataScope(DataScopeEnum.CUSTOM.getValue());
            userDetail.setDataScopeList(deptIds);
        }
    }
}
