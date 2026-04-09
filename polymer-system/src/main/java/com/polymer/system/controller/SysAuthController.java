package com.polymer.system.controller;

import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.security.core.utils.TokenUtils;
import com.polymer.system.entity.SysUserTokenEntity;
import com.polymer.system.query.SysAccountLoginQuery;
import com.polymer.system.service.SysAuthService;
import com.polymer.system.service.SysCaptchaService;
import com.polymer.system.vo.AccessTokenVO;
import com.polymer.system.vo.SysCaptchaVO;
import com.polymer.system.vo.SysMobileLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 认证管理
 *
 * @author polymer
 */
@RestController
@RequestMapping("sys/auth")
@Tag(name = "认证管理")
public class SysAuthController {
    @Resource
    private SysCaptchaService sysCaptchaService;
    @Resource
    private SysAuthService sysAuthService;

    @GetMapping("captcha")
    @Operation(summary = "验证码")
    public Result<SysCaptchaVO> captcha() {
        SysCaptchaVO captchaVO = sysCaptchaService.generate();

        return Result.ok(captchaVO);
    }

    @GetMapping("captcha/enabled")
    @Operation(summary = "是否开启验证码")
    public Result<Boolean> captchaEnabled() {
        boolean enabled = sysCaptchaService.isCaptchaEnabled();

        return Result.ok(enabled);
    }

    @PostMapping("login")
    @Operation(summary = "账号密码登录")
    public Result<SysUserTokenEntity> login(@RequestBody SysAccountLoginQuery login) {
        SysUserTokenEntity token = sysAuthService.loginByAccount(login);

        return Result.ok(token);
    }

    @PostMapping("send/code")
    @Operation(summary = "发送短信验证码")
    public Result<String> sendCode(String mobile) {
        boolean flag = sysAuthService.sendCode(mobile);
        if (!flag) {
            return Result.error("短信发送失败！");
        }

        return Result.ok();
    }

    @PostMapping("mobile")
    @Operation(summary = "手机号登录")
    public Result<SysUserTokenEntity> mobile(@RequestBody SysMobileLoginVO login) {
        SysUserTokenEntity token = sysAuthService.loginByMobile(login);

        return Result.ok(token);
    }

    @PostMapping("token")
    @Operation(summary = "获取 accessToken")
    public Result<AccessTokenVO> token(String refreshToken) {
        AccessTokenVO token = sysAuthService.getAccessToken(refreshToken);

        return Result.ok(token);
    }

    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result<String> logout(HttpServletRequest request) {
        sysAuthService.logout(TokenUtils.getAccessToken(request));

        return Result.ok();
    }
}
