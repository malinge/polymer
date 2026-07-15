package com.polymer.framework.security.core.service;

import com.polymer.api.system.SysUserApi;
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
    private SysUserApi sysUserApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = sysUserApi.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return userDetails;
    }

}
