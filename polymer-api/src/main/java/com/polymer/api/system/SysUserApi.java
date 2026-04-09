package com.polymer.api.system;

import com.polymer.api.system.dto.SysUserDTO;

import java.util.Collection;
import java.util.List;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.api.module.system
 * CreateTime: 2024-04-01  17:39
 * Description: 用户api
 *
 * @author polymer
 * @version 2.0
 */
public interface SysUserApi {

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    SysUserDTO findUserByUserid(Long userId);

    /**
     * 通过用户ID集合查询用户集合
     *
     * @param userIds 用户ID集合
     * @return 用户对象信息集合
     */
    List<SysUserDTO> findUserByUserIds(Collection<Long> userIds);
}
