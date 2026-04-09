package com.polymer.framework.security.core.mobile;

/**
 * 手机短信登录，验证码效验
 *
 * @author polymer
 */
public interface MobileVerifyCodeService {

    boolean verifyCode(String mobile, String code);
}
