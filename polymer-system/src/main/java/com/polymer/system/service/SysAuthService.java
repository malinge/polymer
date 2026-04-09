package com.polymer.system.service;

import com.polymer.system.entity.SysUserTokenEntity;
import com.polymer.system.query.SysAccountLoginQuery;
import com.polymer.system.vo.AccessTokenVO;
import com.polymer.system.vo.SysMobileLoginVO;

/**
 * 权限认证服务
 *
 * @author polymer
 */
public interface SysAuthService {

    /**
     * 账号密码登录
     *
     * @param login 登录信息
     */
    SysUserTokenEntity loginByAccount(SysAccountLoginQuery login);

    /**
     * 手机短信登录
     *
     * @param login 登录信息
     */
    SysUserTokenEntity loginByMobile(SysMobileLoginVO login);

    /**
     * 发送手机验证码
     *
     * @param mobile 手机号
     */
    boolean sendCode(String mobile);

    /**
     * 根据刷新Token，获取AccessToken
     *
     * @param refreshToken refreshToken
     */
    AccessTokenVO getAccessToken(String refreshToken);

    /**
     * 退出登录
     *
     * @param accessToken accessToken
     */
    void logout(String accessToken);
}
