package com.polymer.system.mapper;

import com.polymer.system.entity.SysUserRoleEntity;
import com.polymer.system.query.SysUserRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 用户角色关系
 *
 * @author polymer
 */
@Mapper
public interface SysUserRoleMapper {

    /**
     * 角色ID列表
     *
     * @param userId 用户ID
     * @return 返回角色ID列表
     */
    List<Long> getRoleIdList(Long userId);

    /**
     * 根据角色id查询用户角色关系集合
     *
     * @param roleId 角色id
     * @return 结果
     */
    List<SysUserRoleEntity> getUserIdList(Long roleId);

    /**
     * 根据用户id集合和角色id集合删除用户角色关系信息
     *
     * @param userIdList 用户id集合
     * @param roleIdList 角色id集合
     * @return 结果
     */
    int deleteByUserIdAndRoleId(@Param("userIdList") Collection<Long> userIdList, @Param("roleIdList") Collection<Long> roleIdList);

    /**
     * 查询用户角色关系列表
     *
     * @param query 用户角色关系查询
     * @return 用户角色关系集合
     */
    List<SysUserRoleEntity> selectSysUserRoleList(SysUserRoleQuery query);

    /**
     * 查询用户角色关系
     *
     * @param id 用户角色关系主键
     * @return 用户角色关系
     */
    SysUserRoleEntity selectSysUserRoleById(Long id);

    /**
     * 新增用户角色关系
     *
     * @param sysUserRole 用户角色关系
     * @return 结果
     */
    int insertSysUserRole(SysUserRoleEntity sysUserRole);

    /**
     * 修改用户角色关系
     *
     * @param sysUserRole 用户角色关系
     * @return 结果
     */
    int updateSysUserRole(SysUserRoleEntity sysUserRole);

    /**
     * 删除用户角色关系
     *
     * @param id 用户角色关系主键
     * @return 结果
     */
    int deleteSysUserRoleById(Long id);

}