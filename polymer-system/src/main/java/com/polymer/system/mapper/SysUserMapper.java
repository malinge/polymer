package com.polymer.system.mapper;

import com.polymer.framework.mybatis.core.annotation.DataScope;
import com.polymer.system.entity.SysUserEntity;
import com.polymer.system.query.SysRoleUserQuery;
import com.polymer.system.query.SysUserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 系统用户
 *
 * @author polymer
 */
@Mapper
public interface SysUserMapper {

    /**
     * 根据用户查询用户集合
     *
     * @param query 用户查询
     * @return 结果
     */
    @DataScope(alias = "u")
    List<SysUserEntity> selectSysUserList(SysUserQuery query);

    /**
     * 根据分配角色查询用户集合
     *
     * @param query 分配角色查询
     * @return 结果
     */
    @DataScope(alias = "t1")
    List<SysUserEntity> getRoleUserList(SysRoleUserQuery query);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    SysUserEntity getByUsername(String username);

    /**
     * 根据手机号码查询用户信息
     *
     * @param mobile 手机号码
     * @return 结果
     */
    SysUserEntity selectSysUserByMobile(String mobile);

    /**
     * 根据部门id获取用户个数
     *
     * @param deptId 部门id
     * @return 结果
     */
    int selectCountByDeptId(Long deptId);

    /**
     * 查询用户管理
     *
     * @param id 用户管理主键
     * @return 用户管理
     */
    SysUserEntity selectSysUserById(Long id);

    /**
     * 新增用户管理
     *
     * @param sysUser 用户管理
     * @return 结果
     */
    int insertSysUser(SysUserEntity sysUser);

    /**
     * 修改用户管理
     *
     * @param sysUser 用户管理
     * @return 结果
     */
    int updateSysUser(SysUserEntity sysUser);

    /**
     * 删除用户管理
     *
     * @param id 用户管理主键
     * @return 结果
     */
    int deleteSysUserById(Long id);

    /**
     * 根据id修改用户头像
     *
     */
    int updateUserAvatar(@Param("userId") Long userId, @Param("avatar") String avatar);

    /**
     * 通过用户ID集合查询用户集合
     *
     * @param list 用户ID集合
     * @return 用户对象信息集合
     */
    List<SysUserEntity> selectSysUserIds(Collection<Long> list);
}