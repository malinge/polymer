package com.polymer.system.service.impl;

import com.polymer.api.message.SmsApi;
import com.polymer.framework.common.constant.Constant;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.utils.RandomUtils;
import com.polymer.framework.security.core.cache.TokenStoreCache;
import com.polymer.framework.security.core.mobile.MobileAuthenticationToken;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.entity.SysUserTokenEntity;
import com.polymer.system.enums.LoginOperationEnum;
import com.polymer.system.query.SysAccountLoginQuery;
import com.polymer.system.service.SysAuthService;
import com.polymer.system.service.SysCaptchaService;
import com.polymer.system.service.SysLogLoginService;
import com.polymer.system.service.SysUserService;
import com.polymer.system.service.SysUserTokenService;
import com.polymer.system.vo.AccessTokenVO;
import com.polymer.system.vo.SysMobileLoginVO;
import com.polymer.system.vo.SysUserVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 权限认证服务
 *
 * @author polymer
 */
@Service
public class SysAuthServiceImpl implements SysAuthService {
    @Resource
    private SysCaptchaService sysCaptchaService;
    @Resource
    private TokenStoreCache tokenStoreCache;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private SysLogLoginService sysLogLoginService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserTokenService sysUserTokenService;
    @Resource
    private SmsApi smsApi;

    /**
     * 账号密码登录
     *
     * @param login 登录信息
     */
    @Override
    public SysUserTokenEntity loginByAccount(SysAccountLoginQuery login) {
        // 验证码效验
        boolean flag = sysCaptchaService.validate(login.getKey(), login.getCaptcha());
        if (!flag) {
            // 保存登录日志
            sysLogLoginService.saveLogLogin(login.getUsername(), Constant.FAIL, LoginOperationEnum.CAPTCHA_FAIL.getValue());

            throw new ServiceException("验证码错误");
        }

        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ServiceException("用户名或密码错误");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenEntity userToken = sysUserTokenService.createToken(user.getId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userToken.getAccessToken(), user);

        //
        if (tokenStoreCache.getKickOut()) {
            tokenStoreCache.saveAccessToken(user.getId(), userToken.getAccessToken());
        }
        return userToken;
    }

    /**
     * 手机短信登录
     *
     * @param login 登录信息
     */
    @Override
    public SysUserTokenEntity loginByMobile(SysMobileLoginVO login) {
        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new MobileAuthenticationToken(login.getMobile(), login.getCode()));
        } catch (BadCredentialsException e) {
            throw new ServiceException("手机号或验证码错误");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenEntity userTokenVO = sysUserTokenService.createToken(user.getId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    /**
     * 发送手机验证码
     *
     * @param mobile 手机号
     */
    @Override
    public boolean sendCode(String mobile) {
        // 生成6位验证码
        String code = RandomUtils.randomNumbers(6);

        SysUserVO user = sysUserService.getByMobile(mobile);
        if (user == null) {
            throw new ServiceException("手机号未注册");
        }

        // 发送短信
        return smsApi.sendCode(mobile, "code", code);
    }

    /**
     * 根据刷新Token，获取AccessToken
     *
     * @param refreshToken refreshToken
     */
    @Override
    public AccessTokenVO getAccessToken(String refreshToken) {
        SysUserTokenEntity token = sysUserTokenService.refreshToken(refreshToken);

        // 封装 AccessToken
        AccessTokenVO accessToken = new AccessTokenVO();
        accessToken.setAccessToken(token.getAccessToken());
        //accessToken.setAccessTokenExpire(token.getAccessTokenExpire());

        return accessToken;
    }

    /**
     * 退出登录
     *
     * @param accessToken accessToken
     */
    @Override
    public void logout(String accessToken) {
        // 用户信息
        UserDetail user = tokenStoreCache.getUser(accessToken);

        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);

        // Token过期
        sysUserTokenService.expireToken(user.getId());

        // 保存登录日志
        sysLogLoginService.saveLogLogin(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
    }
}
