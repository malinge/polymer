package com.polymer.framework.security.core.service;

/**
 * 手机短信登录，验证码效验
 *
 * @author polymer
 */
@FunctionalInterface
public interface MobileVerifyCodeService {

    boolean verifyCode(String mobile, String code);
}
