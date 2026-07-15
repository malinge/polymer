package com.polymer.system.api;

import com.polymer.api.system.SysUserApi;
import com.polymer.api.system.dto.SysUserDTO;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.api.system.user.UserDetail;
import com.polymer.system.entity.SysUserEntity;
import com.polymer.system.service.SysUserDetailsService;
import com.polymer.system.service.SysUserService;
import com.polymer.system.vo.SysUserVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.system.api
 * CreateTime: 2024-04-02  08:38
 * Description: 用户api实现
 *
 * @author polymer
 * @version 2.0
 */
@Component
public class SysUserApiImpl implements SysUserApi {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserDetailsService sysUserDetailsService;

    /**
     * 根据用户id查询邮箱
     *
     * @param userId 用户id
     * @return 用户邮箱
     */
    @Override
    public SysUserDTO findUserByUserid(Long userId) {
        SysUserEntity sysUserEntity = sysUserService.selectSysUserById(userId);
        return ConvertUtils.convertTo(sysUserEntity, SysUserDTO::new);
    }

    /**
     * 通过用户ID集合查询用户集合
     *
     * @param userIds 用户ID集合
     * @return 用户对象信息集合
     */
    @Override
    public List<SysUserDTO> findUserByUserIds(Collection<Long> userIds) {
        List<SysUserEntity> userEntities = sysUserService.selectSysUserIds(userIds);
        return ConvertUtils.convertListTo(userEntities, SysUserDTO::new);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        SysUserVO vo = sysUserService.getByMobile(mobile);
        if(vo == null){
            return null;
        }
        return sysUserDetailsService.getUserDetails(ConvertUtils.convertTo(vo, UserDetail::new));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUserVO vo = sysUserService.getByUsername(username);
        if(vo == null){
            return null;
        }
        return sysUserDetailsService.getUserDetails(ConvertUtils.convertTo(vo, UserDetail::new));
    }
}
