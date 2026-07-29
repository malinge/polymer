package com.polymer.system.service;

import com.polymer.framework.security.core.user.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;

public interface SysUserDetailsService {

    /**
     * 获取 UserDetails 对象，设置用户权限信息
     *
     * @param userDetail 登录用户信息
     * @return UserDetails
     */
    UserDetails getUserDetails(UserDetail userDetail);
}
