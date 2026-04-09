package com.polymer.system.security.service;

import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.security.core.mobile.MobileUserDetailsService;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.entity.SysUserEntity;
import com.polymer.system.mapper.SysUserMapper;
import com.polymer.system.service.SysUserDetailsService;
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
    private SysUserDetailsService sysUserDetailsService;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserMapper.selectSysUserByMobile(mobile);
        if (userEntity == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }
        UserDetail userDetail = ConvertUtils.convertTo(userEntity, UserDetail::new);
        return sysUserDetailsService.getUserDetails(userDetail);
    }

}
