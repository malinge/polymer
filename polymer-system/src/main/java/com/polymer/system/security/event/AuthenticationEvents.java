package com.polymer.system.security.event;

import com.polymer.framework.common.constant.Constant;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.enums.LoginOperationEnum;
import com.polymer.system.service.SysLogLoginService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 认证事件处理
 *
 * @author polymer
 */
@Component
public class AuthenticationEvents {
    @Resource
    private SysLogLoginService sysLogLoginService;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 用户信息
        UserDetail user = (UserDetail) event.getAuthentication().getPrincipal();

        // 保存登录日志
        sysLogLoginService.saveLogLogin(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGIN_SUCCESS.getValue());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // 用户名
        String username = (String) event.getAuthentication().getPrincipal();

        // 保存登录日志
        sysLogLoginService.saveLogLogin(username, Constant.FAIL, LoginOperationEnum.ACCOUNT_FAIL.getValue());
    }

}