package com.polymer.system.security.service;

import com.polymer.api.message.SmsApi;
import com.polymer.framework.security.core.mobile.MobileVerifyCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 短信验证码效验
 *
 * @author polymer
 */
@Service
public class MobileVerifyCodeServiceImpl implements MobileVerifyCodeService {
    @Resource
    private SmsApi smsApi;

    @Override
    public boolean verifyCode(String mobile, String code) {
        return smsApi.verifyCode(mobile, code);
    }
}
