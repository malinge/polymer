package com.polymer.system.security;

import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.security.core.service.MobileUserDetailsService;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.service.SysUserDetailsService;
import com.polymer.system.service.SysUserService;
import com.polymer.system.vo.SysUserVO;
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
    private SysUserService sysUserService;
    @Resource
    private SysUserDetailsService sysUserDetailsService;

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {

        SysUserVO vo = sysUserService.getByMobile(mobile);
        if(vo == null){
            throw new UsernameNotFoundException("手机号或验证码错误");
        }
        return sysUserDetailsService.getUserDetails(ConvertUtils.convertTo(vo, UserDetail::new));
    }

}
