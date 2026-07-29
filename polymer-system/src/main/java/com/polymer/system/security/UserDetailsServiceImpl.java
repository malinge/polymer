package com.polymer.system.security;

import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.service.SysUserDetailsService;
import com.polymer.system.service.SysUserService;
import com.polymer.system.vo.SysUserVO;
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
    private SysUserService sysUserService;
    @Resource
    private SysUserDetailsService sysUserDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserVO vo = sysUserService.getByUsername(username);
        if(vo == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return sysUserDetailsService.getUserDetails(ConvertUtils.convertTo(vo, UserDetail::new));
    }

}
