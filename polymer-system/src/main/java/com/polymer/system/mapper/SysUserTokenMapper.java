package com.polymer.system.mapper;

import com.polymer.system.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.system.dao
 * CreateTime: 2023-08-18  15:49
 * Description: 用户token
 *
 * @author polymer
 * @version 2.0
 */
@Mapper
public interface SysUserTokenMapper {

    /**
     * 根据角色ID，查询在线用户 access_token 列表
     *
     * @param roleId 角色ID
     * @param time   当前时间
     * @return 返回 access_token 列表
     */
    List<String> getOnlineAccessTokenListByRoleId(@Param("roleId") Long roleId, @Param("time") LocalDateTime time);

    /**
     * 根据用户ID，查询在线用户 access_token 列表
     *
     * @param userId 用户ID
     * @param time   当前时间
     * @return 返回 access_token 列表
     */
    List<String> getOnlineAccessTokenListByUserId(@Param("userId") Long userId, @Param("time") LocalDateTime time);

    /**
     * 更新用户token信息
     *
     * @param entity 用户token
     * @return 结果
     */
    int updateByUserId(SysUserTokenEntity entity);

    /**
     * 根据用户id查询一条用户token信息
     *
     * @param userId 用户id
     * @return 结果
     */
    SysUserTokenEntity selectUserTokenOne(Long userId);

    /**
     * 保存用户token信息
     *
     * @param entity 用户token信息
     * @return 结果
     */
    int insertUserToken(SysUserTokenEntity entity);

    /**
     * 根据刷新token查询一条用户token信息
     *
     * @param refreshToken 刷新token
     * @return 结果
     */
    SysUserTokenEntity selectOneByRefreshToken(String refreshToken);

    /**
     * 修改用户Token
     *
     * @param sysUserToken 用户Token
     * @return 结果
     */
    int updateSysUserToken(SysUserTokenEntity sysUserToken);
}
