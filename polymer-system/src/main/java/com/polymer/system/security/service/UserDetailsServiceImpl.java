package com.polymer.system.security.service;

import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.entity.SysUserEntity;
import com.polymer.system.mapper.SysUserMapper;
import com.polymer.system.service.SysUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 账号登录 UserDetailsService
 *
 * @author polymer
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private SysUserDetailsService sysUserDetailsService;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserMapper.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return sysUserDetailsService.getUserDetails(ConvertUtils.convertTo(userEntity, UserDetail::new));
    }

}
