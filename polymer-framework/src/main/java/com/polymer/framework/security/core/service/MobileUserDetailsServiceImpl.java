package com.polymer.framework.security.core.service;

import com.polymer.api.system.SysUserApi;
import com.polymer.framework.security.core.mobile.MobileUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 手机验证码登录 MobileUserDetailsService
 *
 * @author polymer
 */
@Service
public class MobileUserDetailsServiceImpl implements MobileUserDetailsService {
    @Resource
    private SysUserApi sysUserApi;

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        UserDetails userDetails = sysUserApi.loadUserByMobile(mobile);
        if (userDetails == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }
        return userDetails;
    }

}
